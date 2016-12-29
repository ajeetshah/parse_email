package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    private MailContentBuilder mailContentBuilder;

    private Logger logger = Logger.getLogger(Application.class.getName());

    public void sendEmail() throws MailException{

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            String id_feedback = "ABC-29122016-QW-24";
            String content = mailContentBuilder.build("Hi", id_feedback);
            messageHelper.setText(content, true);
            messageHelper.setTo("me@myemail.com");
            messageHelper.setSubject("My Email Subject - " + id_feedback);
        };
        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            logger.info("Email not sent " + e.getMessage());
        }
    }
}
