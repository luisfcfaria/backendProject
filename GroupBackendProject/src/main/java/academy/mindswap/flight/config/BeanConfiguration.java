package academy.mindswap.flight.config;


import academy.mindswap.flight.gatewayemail.SendGridProperties;
import academy.mindswap.flight.security.AuthorizationValidation;
import com.sendgrid.SendGrid;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.concurrent.Executor;

@Configuration
public class BeanConfiguration {


    private SendGridProperties sendGrid;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public BCryptPasswordEncoder bcryptEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-pool-");
        executor.initialize();
        return executor;
    }

    @Bean
    public AuthorizationValidation authorized() {
        return new AuthorizationValidation();
    }

}
