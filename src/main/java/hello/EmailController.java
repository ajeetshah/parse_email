package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mail.MailException;

import java.util.logging.Logger;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;
    private Logger logger = Logger.getLogger(Application.class.getName());

    @RequestMapping("/email/check")
    public String check(){
        String response = "Finished Checking Mails ... ";

        String host = "imap.gmail.com";
        String mailStoreType = "imap";
        String username = "you@gmail.com";
        String password = "password";

        CheckingMails.check(host, mailStoreType, username, password);
        System.out.println(response);
        return response;
    }

    @RequestMapping("/email/fetch")
    public String fetch() {
        System.out.println("Fetching mail");
        String response = "Finished Fetching Mails ...";

        String host = "imap.gmail.com";
        String mailStoreType = "imap";
        String username =  "you@gmail.com";
        String password = "password";

        FetchingMails.fetch(host, mailStoreType, username, password);
        System.out.println(response);
        return response;
    }

    @RequestMapping("/email/send")
    public String send() throws MailException {
        System.out.println("Sending mail");
        String response;
        try {
            emailService.sendEmail();
            response = "Mail sent";
        }
        catch (MailException ex){
            System.err.println(ex.getMessage());
            response = "Mail not sent";
            logger.info( response + " :" + ex.getMessage());
        }
        System.out.println(response);
        return response;
    }
}
