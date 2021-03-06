package account;

import account.domain.Account;
import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import mail.MailPlugin;
import memdb.MemdbPlugin;
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
        return ImmutableSet.of(new MailPlugin(), new MemdbPlugin());
    }

    @Override
    public void afterDataBaseInitial() {
        Optional<Account> admin = Account.finder.byKey(ADMIN_ACCOUNT);
        if (!admin.isPresent()) {
            Account acc = new Account(ADMIN_ACCOUNT, "123456");
            acc.save();
            admin = Account.finder.byKey(ADMIN_ACCOUNT);
        }
        admin.ifPresent(a -> Account.setAdminId(a.getId()));

        Optional<Account> guest = Account.finder.byKey(GUEST_ACCOUNT);
        if (!guest.isPresent()) {
            Account acc = new Account(GUEST_ACCOUNT, "123456");
            acc.save();
            guest = Account.finder.byKey(GUEST_ACCOUNT);
        }
        guest.ifPresent(a -> Account.setGuestId(a.getId()));
    }
}
