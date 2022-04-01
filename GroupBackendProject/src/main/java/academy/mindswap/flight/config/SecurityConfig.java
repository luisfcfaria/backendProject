package academy.mindswap.flight.config;


import academy.mindswap.flight.security.CookieFilter;
import academy.mindswap.flight.security.MyAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {



    private MyAuthProvider myAuthenticationProvider;

   /* @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(HttpMethod.POST,"/auth/login");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/auth/login").permitAll()
                //.antMatchers( "/flights/**").authenticated()
               // .antMatchers(
          //      HttpMethod.GET,
          //      "/api/*/1","/login")
          //      .authenticated()
                //.permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new CookieFilter(myAuthenticationProvider), BasicAuthenticationFilter.class);

             //   .and()
        ;
      //  super.configure(http);
    }


    @Autowired
    public void setMyAuthenticationProvider(MyAuthProvider myAuthenticationProvider) {
        this.myAuthenticationProvider = myAuthenticationProvider;
    }
}