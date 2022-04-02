package academy.mindswap.flight.gatewayemail;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class SendGridGateway implements EmailGateway {

    private static final Logger LOGGER = LogManager.getLogger(SendGridGateway.class);

    private static final String FROM_EMAIL = "os.fixes.da.mindswap@gmail.com";

    private final SendGrid sendGrid;
    private final SendGridProperties sendGridProperties;
    private final EmailProperties emailProperties;

    public SendGridGateway(SendGrid sendGrid, SendGridProperties sendGridProperties, EmailProperties emailProperties) {
        this.sendGrid = sendGrid;
        this.sendGridProperties = sendGridProperties;
        this.emailProperties = emailProperties;
    }

    @Async
    @Override
    public void sendEmail(String email, Map<String, String> variables, EmailTemplate template) {

        if (!sendGridProperties.isEnabled()) {
            return;
        }

        Email from = new Email(FROM_EMAIL);
        Email to = new Email(email);

        Personalization personalization = new Personalization();
        variables.forEach(personalization::addDynamicTemplateData);
        personalization.addTo(to);

        Mail mail = new Mail();
        mail.setTemplateId(EmailTemplate.CANCELED_FLIGHT.getName());
        mail.setFrom(from);
        mail.addPersonalization(personalization);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            sendGrid.api(request);

            LOGGER.info(String.format("Email %s sent successfully to %s", template.getName(), email));
        } catch (IOException ex) {
            LOGGER.error(String.format("Failed to send email %s to %s", template.getName(), email));
        }
    }
}