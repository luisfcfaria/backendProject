package academy.mindswap.flight.services;


import academy.mindswap.flight.commands.EmailData;
import academy.mindswap.flight.gatewayemail.EmailGateway;
import academy.mindswap.flight.gatewayemail.EmailTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${platforn.fe.baseUrl:baseUrl}")
    private String baseAppUrl;

    private final EmailGateway emailGateway;

    public EmailServiceImpl(EmailGateway emailGateway) {
        this.emailGateway = emailGateway;
    }

    @Override
    public void sendEmail(EmailData emailData, EmailTemplate template) {

        String name = emailData.getName();

        Map<String, String> variables = new HashMap<>();
        variables.put("firstName", name);

        emailGateway.sendEmail(
                emailData.getEmail(),
                variables,
                template
        );
    }
}
