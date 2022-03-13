package org.aquam.learnrest.config;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.util.Arrays;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationSuccessHandler SuccessLoginHandler;

    // какие пути защищены, а какие нет
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   // ENABLE
                .authorizeRequests()
                // эти всем разрешены - тут url
                    .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/uploads/**", "/icons/**").permitAll()
                    .antMatchers("/learn", "/learn/login", "/learn/register/**").permitAll()
                .antMatchers("/learn/subjects/**").permitAll()
                // любой другой запрос (any) только для authenticated
                    .anyRequest().authenticated()
                // а тут настройки для входа и выхода
                //.and().formLogin().loginPage("/learn/login").permitAll()
                .and().formLogin().loginPage("/learn/login").defaultSuccessUrl("/learn/home", true)
                .successHandler(SuccessLoginHandler)
                .and().logout().invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/learn/logout")).logoutSuccessUrl("/learn/logout-success").permitAll()
        ;
    }

    // настройка хранилища пользователей - через authenticationProvider
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    // обработка аутентификации пользователя
    // Data Access object if we want to connect to a db
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public FilterRegistrationBean hiddenHttpMethodFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new HiddenHttpMethodFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        return filterRegistrationBean;
    }

}
