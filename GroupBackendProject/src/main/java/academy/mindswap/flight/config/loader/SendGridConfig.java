package academy.mindswap.flight.config.loader;

import academy.mindswap.flight.gatewayemail.SendGridProperties;
import com.sendgrid.SendGrid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@Configuration
public class SendGridConfig {

    private final SendGridProperties sendGridProperties;

//    /**
//     * CORS configuration
//     * @return {@link FilterRegistrationBean}
//     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

//    /**
//     * PasswordEncoder configuration
//     * Set the password encoder we want to use returned a generic {@link PasswordEncoder}
//     * @return {@link PasswordEncoder}
//     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SendGrid getSendGrid() {
        return new SendGrid(sendGridProperties.getApiKey());
    }
}