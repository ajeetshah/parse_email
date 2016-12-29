package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;
    private Logger logger = Logger.getLogger(Application.class.getName());

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(String message, String id_token) {
        Map<String, String> variables = new HashMap<String, String>();
        variables.put("message", "Hi");
        variables.put("id_token", id_token);

        Context context = new Context();
        String result;
        context.setVariables(variables);
        try{
            result = templateEngine.process("mailTemplate", context);
        }
        catch (Exception ex){
            result = "Unable to parse the HTML mail template";
            logger.info(result + ": " + ex.getMessage());
        }
        return result;
    }
}
