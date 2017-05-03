package rsvier;

/**
 * Created by J on 5/1/2017.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rsvier.user.CurrentUserDetailsService;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    DataSource dataSource;
    @Autowired
    CurrentUserDetailsService userDetailsService;

    @Autowired
    public void configAuth(AuthenticationManagerBuilder auth) throws Exception {
        /*
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
        auth
                .jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select email,pass_hash, enabled from users where email=?")
                .authoritiesByUsernameQuery(
                        "select email, role from roles where email=?");
        */
        auth
                .userDetailsService(userDetailsService);
    }

}