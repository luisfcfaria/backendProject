package academy.mindswap.flight.config;
//
//import com.sendgrid.*;
//import org.springframework.boot.autoconfigure.web.WebProperties;
//
//
//import java.io.IOException;
//
//public class EmailConfig {
//    public void sendMail() throws IOException {
//
//        SendGrid.Email from = new SendGrid.Email("test@example.com");
//        String subject = "Sending with SendGrid is Fun";
//        SendGrid.Email to = new SendGrid.Email("test@example.com");
//        WebProperties.Resources.Chain.Strategy.Content content = new WebProperties.Resources.Chain.Strategy.Content("text/plain", "and easy to do anywhere, even with Java");
//        Mail mail = new Mail(from, subject, to, content);
//
//        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
//
//        Request request = new Request();
//
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
//        } catch (IOException ex) {
//            throw ex;
//        }
//    }
//}