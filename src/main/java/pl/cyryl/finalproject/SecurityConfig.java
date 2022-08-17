package pl.cyryl.finalproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.cyryl.finalproject.users.authentication.CustomOAuth2UserService;
import pl.cyryl.finalproject.users.authentication.ExternalAuthSuccessHandler;
import pl.cyryl.finalproject.users.authentication.RegularAuthSuccessHandler;
import pl.cyryl.finalproject.users.details.SpringDataUserDetailsService;
import pl.cyryl.finalproject.util.FilesService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private FilesService filesService;

    @Bean
    public ExternalAuthSuccessHandler externalAuthSuccessHandler() {
        return new ExternalAuthSuccessHandler();
    }

    @Bean
    public RegularAuthSuccessHandler regularAuthSuccessHandler() {
        return new RegularAuthSuccessHandler();
    }

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
                .antMatchers("/", filesService.getProfilePicturesDirectory() + "**").permitAll()
                .antMatchers("/register/**").anonymous()
                .antMatchers("/admin/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/**")
                .hasAnyRole("USER", "ADMIN")
                .and().formLogin().permitAll()
                    .loginPage("/login")
                    .successHandler(regularAuthSuccessHandler())
                .and().oauth2Login()
                    .loginPage("/login")
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                    .and().successHandler(externalAuthSuccessHandler())
                .and().logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                .and().exceptionHandling().accessDeniedPage("/403");
    }
}
