package hello;

import javax.mail.*;
import javax.mail.search.SearchTerm;
import java.util.Properties;

public class CheckingMails {

    public static void check(String host, String storeType, String user, String password) {
        try {

            Properties properties = new Properties();
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.ssl.enable", "true");
            properties.put("mail.imap.socketFactory.class" , "javax.net.ssl.SSLSocketFactory" );

            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imap");
            store.connect(host, user, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            SearchTerm term = new SearchTerm() {
                public boolean match(Message message) {
                    try {
                        if (message.getSubject() != null && message.getSubject().toLowerCase().contains("anything you want to search for")) {
                            return true;
                        }
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }
            };
            Message[] messages = emailFolder.search(term);
            System.out.println("messages.length: " + messages.length);

            int n = messages.length;
            for (int i = n-1; i >= 0; i--) {
                Message message = messages[i];
                System.out.println("Email: " + (i + 1) + " ---------------------------------");
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());
            }

            emailFolder.close(false);
            store.close();
            System.out.println("Finished Checking Mails ...");

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
