package account.token;

import account.AccountPlugin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public final class Tokens {
    private Tokens() {
    }

    public static String newToken(Long accountId) {
        return Jwts.builder()
                .claim("accountId", accountId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, AccountPlugin.secretKey())
                .compact();
    }

    public static Long accountId(String token) {
        final Claims claims = Jwts.parser().setSigningKey(AccountPlugin.secretKey())
                .parseClaimsJws(token).getBody();
        return Long.valueOf(String.valueOf(claims.get("accountId")));
    }
}
