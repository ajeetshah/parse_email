package hello;

import javax.mail.*;
import javax.mail.search.SearchTerm;
import java.io.*;
import java.util.Date;
import java.util.Properties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class FetchingMails {

    public static void fetch(String imapHost, String storeType, String user, String password) {
        try {
            Properties properties = new Properties();
            properties.put("mail.imap.host", imapHost);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.ssl.enable", "true");
            properties.put("mail.imap.socketFactory.class" , "javax.net.ssl.SSLSocketFactory" );

            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imap");
            store.connect(imapHost, user, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            SearchTerm term = new SearchTerm() {
                public boolean match(Message message) {
                    try {
                        String subject = "ABC-29122016-QW-24".toLowerCase();
                        if (message.getSubject() != null && message.getSubject().toLowerCase().contains(subject)) {
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
                System.out.println("Email: " + (i + 1) + " -----------------");
                writePart(message);
            }

            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writePart(Part p) throws Exception {
        if (p instanceof Message)
            writeEnvelope((Message) p);

        if (p.isMimeType("text/plain")) {
            // we are looking for TEXT/HTML
        }

        else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++)
                writePart(mp.getBodyPart(i));
        }

        else {
            Object o = p.getContent();
            if (o instanceof String) {
                parseEmail((String) o);
            }
            else {
                System.out.println("This is an unknown type:");
                System.out.println(o.toString());
            }
        }
    }

    public static void writeEnvelope(Message m) throws Exception {
        Address[] a;

        if ((a = m.getFrom()) != null) {
            for (int j = 0; j < a.length; j++)
                System.out.println("FROM: " + a[j].toString());
        }

        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
            for (int j = 0; j < a.length; j++)
                System.out.println("TO: " + a[j].toString());
        }

        if (m.getSubject() != null)
            System.out.println("SUBJECT: " + m.getSubject());
    }

    public static void parseEmail(String HTMLString){
        Document html = Jsoup.parse(HTMLString);
        String h1 = html.body().getElementsByTag("h1").text();

        String id_name = "id_name";
        String id_comment = "id_comment";
        String id_token = "id_token";

        String name = html.body().getElementById(id_name).text().trim();
        String comment = html.body().getElementById(id_comment).text().trim();
        String tokenId = html.body().getElementById(id_token).text().trim();

        System.out.println("name:" + name + " comment: "+ comment + " feedbackId: " + tokenId);
    }
}
