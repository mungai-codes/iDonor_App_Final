package net.mungai.idonor.app.security;

import net.mungai.idonor.admin.service.AdminDetailsImpl;
import net.mungai.idonor.donor.service.DonorDetailsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Configuration
    @Order(1)
    public static class UserSecurityConfig  extends WebSecurityConfigurerAdapter  {
        @Bean
        public UserDetailsService donorDetailsService() {
            return new DonorDetailsImpl();
        }

        @Bean
        public PasswordEncoder donorEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider donorAuthenticationProvider() {

            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(donorDetailsService());
            authProvider.setPasswordEncoder(donorEncoder());

            return authProvider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(donorAuthenticationProvider());

        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/donor/**")
                    .authorizeRequests()
                    .anyRequest()
                    .hasAuthority("user")
                    .and()
                    .formLogin()
                    .loginPage("/donor/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .failureUrl("/donor/login?error")
//                    .defaultSuccessUrl("/donor/dashboard", true)
                    .loginProcessingUrl("/donor/login")
//                    .defaultSuccessUrl("/donor/dashboard", true)
                    .permitAll()
                    .and()
                    .rememberMe().key("AbcdEfghIjklmNopQrsTuvwXyz_0123456789")
                    .and()
                    .logout()
                    .logoutUrl("/donor/logout")
                    .logoutSuccessUrl("/donor/login")
                    .and()
                    .csrf().disable();
        }
    }

    @Configuration
    @Order(2)
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter  {

        @Bean
        public UserDetailsService adminDetailsService() {
            return new AdminDetailsImpl();
        }

        @Bean
        public PasswordEncoder adminEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider adminAuthenticationProvider() {

            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(adminDetailsService());
            authProvider.setPasswordEncoder(adminEncoder());

            return authProvider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(adminAuthenticationProvider());

        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .antMatcher("/admin/**")
                    .authorizeRequests()
                    .anyRequest()
                    .hasAuthority("admin")
                    .and()
                    .formLogin()
                    .loginPage("/admin/login")
                    .loginProcessingUrl("/admin/login")
                    .usernameParameter("text")
                    .passwordParameter("password")
                    .failureUrl("/admin/login?error")
                    .defaultSuccessUrl("/admin/dashboard", true)
                    .permitAll()
                    .and()
                    .rememberMe().key("AbcdEfghIjklmNopQrsTuvwXyz_0123456789")
                    .and()
                    .logout()
                    .logoutUrl("/admin/logout")
                    .logoutSuccessUrl("/admin/login")
                    .and()
                    .csrf().disable();
        }

    }

}