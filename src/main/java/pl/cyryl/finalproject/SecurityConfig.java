package pl.cyryl.finalproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.cyryl.finalproject.users.authentication.ExternalAuthSuccessHandler;
import pl.cyryl.finalproject.users.authentication.RegularAuthSuccessHandler;
import pl.cyryl.finalproject.users.details.SpringDataUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public ExternalAuthSuccessHandler externalAuthSuccessHandler(){
        return new ExternalAuthSuccessHandler();
    }

    @Bean
    public RegularAuthSuccessHandler regularAuthSuccessHandler() { return new RegularAuthSuccessHandler(); }

    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
        return new SpringDataUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/item/**", "/offer/**")
                .hasAnyRole("USER")
                .and().formLogin().loginPage("/login").successHandler(regularAuthSuccessHandler())
                .and().oauth2Login().loginPage("/login").successHandler(externalAuthSuccessHandler())
                .and().logout().logoutSuccessUrl("/")
                .permitAll()
                .and().exceptionHandling().accessDeniedPage("/403");
    }
}
