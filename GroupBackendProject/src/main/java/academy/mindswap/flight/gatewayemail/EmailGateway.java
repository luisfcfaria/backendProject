package academy.mindswap.flight.gatewayemail;

import org.springframework.scheduling.annotation.Async;

import java.util.Map;

public interface EmailGateway {

    @Async
    void sendEmail(String email, Map<String, String> variables, EmailTemplate template);

}
