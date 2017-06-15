package mail;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:application.properties")
final class MailConfig {
    @Value("${mail.host:127.0.0.1}")
    private String host;

    @Value("${mail.port:25}")
    private int port;

    @Value("${mail.username:guest}")
    private String username;

    @Value("${mail.password:pwd}")
    private String password;

    @Value("${mail.defaultEncoding:UTF-8}")
    private String defaultEncoding;

    @Value("${mail.enableSsl:false}")
    private boolean enableSsl;
}
