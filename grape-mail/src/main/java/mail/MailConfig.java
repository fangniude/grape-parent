package mail;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:application.properties")
public class MailConfig {
    @Value("${mail.host:127.0.0.1}")
    private String host;

    @Value("${mail.port:25}")
    private int port;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.defaultEncoding}")
    private String defaultEncoding;

    @Value("${mail.enableSsl}")
    private boolean enableSsl;
}
