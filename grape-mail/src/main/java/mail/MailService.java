package mail;

import msg.Msg;
import msg.MsgType;
import msg.MsgTypeService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Properties;

@Service
public final class MailService implements MsgTypeService {
    private JavaMailSenderImpl sender = new JavaMailSenderImpl();
    private MailConfig config;

    @Inject
    public MailService(MailConfig config) {
        this.config = config;
    }

    @PostConstruct
    private void init() {
        sender.setHost(config.getHost());
        sender.setPort(config.getPort());
        sender.setUsername(config.getUsername());
        sender.setPassword(config.getPassword());
        sender.setDefaultEncoding(config.getDefaultEncoding());

        if (config.isEnableSsl()) {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", true);
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.timeout", 5000);
            sender.setJavaMailProperties(properties);
        } else {
            sender.setJavaMailProperties(new Properties());
        }
    }

    @Override
    public MsgType msgType() {
        return MsgType.MAIL;
    }

    @Override
    public void send(Msg msg) {
        SimpleMailMessage m = new SimpleMailMessage();
        m.setFrom(config.getUsername()); // 测试无此项或与发送地址不一致会被服务器拒收
        m.setTo(msg.destination);
        m.setSubject(msg.title);
        m.setText(msg.content);
        sender.send(m);
    }
}
