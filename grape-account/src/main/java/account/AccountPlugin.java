package account;

import account.domain.Account;
import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import mail.MailPlugin;
import org.grape.GrapeApp;
import org.grape.GrapePlugin;

import java.util.Optional;
import java.util.Set;

import static account.domain.Account.ADMIN_ACCOUNT;
import static account.domain.Account.GUEST_ACCOUNT;

@AutoService(GrapePlugin.class)
public final class AccountPlugin extends GrapePlugin {
    public static String secretKey() {
        return GrapeApp.getProperty("account.secretKey", "account.secretKey.default");
    }

    public static String authHeader() {
        return GrapeApp.getProperty("account.authorization.header", "token: ");
    }

    @Override
    public Set<GrapePlugin> dependencies() {
        return Sets.newHashSet(new MailPlugin());
    }

    @Override
    public void afterDataBaseInitial() {
        Optional<Account> admin = Account.finder.byKey(ADMIN_ACCOUNT);
        if (!admin.isPresent()) {
            Account acc = new Account();
            acc.setKey(ADMIN_ACCOUNT);
            acc.setPassword("123456");
            acc.save();
            admin = Account.finder.byKey(ADMIN_ACCOUNT);
        }
        admin.ifPresent(a -> Account.setAdminId(a.getId()));

        Optional<Account> guest = Account.finder.byKey(GUEST_ACCOUNT);
        if (!guest.isPresent()) {
            Account acc = new Account();
            acc.setKey(GUEST_ACCOUNT);
            acc.setPassword("123456");
            acc.save();
            guest = Account.finder.byKey(GUEST_ACCOUNT);
        }
        guest.ifPresent(a -> Account.setGuestId(a.getId()));
    }
}
