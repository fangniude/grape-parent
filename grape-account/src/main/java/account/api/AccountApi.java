package account.api;

import account.domain.Account;
import account.token.Tokens;
import memdb.MemTable;
import memdb.MemTableFactory;
import msg.Msg;
import msg.MsgService;
import msg.MsgType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.beans.PropertyEditorSupport;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("account")
public final class AccountApi {
    private MsgService msgService;
    private final MemTable<String> checkCodeTable;

    private Random random = new Random();

    @Inject
    public AccountApi(MsgService msgService, MemTableFactory memTableFactory) {
        this.msgService = msgService;
        this.checkCodeTable = memTableFactory.getOrCreateMemTable("checkCodeTable", String.class, 5 * 60);
    }

    @RequestMapping(path = "/exist/{account:.+}", method = RequestMethod.GET)
    public boolean exist(@PathVariable String account) {
        return Account.findByAccount(account).isPresent();
    }

    @RequestMapping(path = "/msg/{type}/{account:.+}", method = RequestMethod.GET)
    public boolean sendMsg(@PathVariable MsgType type, @PathVariable String account) {
        String checkCode = IntStream.range(0, 6).mapToObj(i -> String.valueOf(random.nextInt(10))).collect(Collectors.joining());
        msgService.send(type, new Msg(account, "checkCode", checkCode));
        checkCodeTable.put(account, checkCode);
        return true;
    }

    @RequestMapping(path = "/register/{type}/{account:.+}/{checkCode}/{pwd}", method = RequestMethod.GET)
    public boolean register(@PathVariable MsgType type, @PathVariable String account, @PathVariable String checkCode, @PathVariable String pwd) {
        if (!checkCode.equals(checkCodeTable.getOrDefault(account, null))) {
            return false;
        }

        Account acc = new Account(account, pwd);
        switch (type) {
            case SMS:
                acc.setPhone(account);
                break;
            case MAIL:
                acc.setMail(account);
                break;
            default:
                checkCodeTable.put(account, "123456");
        }
        acc.save();
        return true;
    }


    @RequestMapping(path = "/login/{account:.+}/{pwd}", method = RequestMethod.GET)
    public ResponseEntity<String> login(@PathVariable String account, @PathVariable String pwd) {
        Optional<Account> optional = Account.findByAccount(account);

        if (optional.isPresent()) {
            Account acc = optional.get();
            if (acc.authenticate(pwd)) {
                return new ResponseEntity<>(Tokens.newToken(acc.getId()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("account or password wrong.", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("account not exist.", HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(path = "/token/update", method = RequestMethod.GET)
    public ResponseEntity<String> updateToken() {
        return new ResponseEntity<>(Tokens.newToken(Account.current()), HttpStatus.OK);
    }

    @RequestMapping(path = "/reset/{type}/{account:.+}/{checkCode}/{pwd}", method = RequestMethod.GET)
    public boolean reset(@PathVariable MsgType type, @PathVariable String account, @PathVariable String checkCode, @PathVariable String pwd) {
        if (!checkCode.equals(checkCodeTable.getOrDefault(account, null))) {
            return false;
        }

        Optional<Account> optional = Account.findByAccount(account);
        if (optional.isPresent()) {
            Account acc = optional.get();
            acc.setPassword(pwd);
            acc.update();
            return true;
        } else {
            return false;
        }
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(MsgType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(MsgType.valueOf(text));
            }
        });
    }
}
