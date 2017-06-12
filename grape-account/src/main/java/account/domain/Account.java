package account.domain;

import lombok.Getter;
import lombok.Setter;
import org.grape.GrapeException;
import org.grape.GrapeFinder;
import org.grape.GrapeModel;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Optional;

@Getter
@Setter
@Entity
@Table(name = "account_account")
public final class Account extends GrapeModel {
    public static final GrapeFinder<Account> finder = new GrapeFinder<>(Account.class);

    private static final ThreadLocal<Long> currentAccountId = new ThreadLocal<>();
    public static final String ADMIN_ACCOUNT = "admin";
    public static final String GUEST_ACCOUNT = "guest";

    @Getter
    @Setter
    private static Long adminId;

    @Getter
    @Setter
    private static Long guestId;

    @Column(length = 64)
    private String mail;

    @Column(length = 32)
    private String phone;

    @Column(length = 32)
    private String password;

    @Column(length = 16)
    private String salt;

    public static void setCurrent(Long accountId) {
        currentAccountId.set(accountId);
    }

    public static void removeCurrent() {
        currentAccountId.remove();
    }

    public static Long current() {
        Long id = currentAccountId.get();
        if (id != null) {
            return id;
        } else {
            return guestId;
        }
    }

    public static Optional<Account> findByAccount(String acc) {
        return finder.query().where().or().eq("primary_key", acc).eq("mail", acc).eq("phone", acc).findOneOrEmpty();
    }

    @Override
    public void save() {
        this.salt = salt();
        this.password = encrypt(this.password, this.salt);
        super.save();
    }

    @Override
    public void update() {
        this.salt = salt();
        this.password = encrypt(this.password, this.salt);
        super.update();
    }

    public boolean authenticate(String pwd) {
        return encrypt(pwd, salt).equals(password);
    }

    private static String salt() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

            byte[] salt = new byte[8];
            random.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        } catch (NoSuchAlgorithmException e) {
            throw new GrapeException(e);
        }
    }

    private static String encrypt(String password, String salt) {
        try {
            String algorithm = "PBKDF2WithHmacSHA1";
            SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

            int derivedKeyLength = 160;
            int iterations = 20000;
            KeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), iterations, derivedKeyLength);

            SecretKey secretKey = f.generateSecret(spec);
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new GrapeException(e);
        }
    }
}
