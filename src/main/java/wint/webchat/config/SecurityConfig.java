package wint.webchat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import wint.webchat.security.CustomAccessDeniedHandler;
import wint.webchat.security.CustomSuccessHandler;
import wint.webchat.service.Impl.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private CustomSuccessHandler customSuccessHandler;
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
               httpSecurity.csrf(csrf->csrf.disable())
                       .authorizeHttpRequests(request->
                               request.requestMatchers("/web/**").permitAll()
                                       .requestMatchers("/register").permitAll()
                                       .requestMatchers("/admin/**").hasAuthority("admin")
                                       .requestMatchers("/**").hasAuthority("user")
                                       .anyRequest().authenticated()
                       ).formLogin(login->login.loginPage("/login")
                               .loginProcessingUrl("/login")
                               .successHandler(customSuccessHandler)
                               .permitAll())
                       .exceptionHandling(exh->exh.accessDeniedHandler(accessDeniedHandler));
       return httpSecurity.build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        var uds=new InMemoryUserDetailsManager();
        var user1= User.withUsername("vdt")
                .password(passwordEncoder().encode("12345678"))
                .authorities("user").build();
        uds.createUser(user1);
        return uds;
    }
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }
}
