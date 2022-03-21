package org.aquam.learnrest.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationSuccessHandler SuccessLoginHandler;

    // hasAuthority("ROLE_ADMIN") = hasRole("ADMIN")
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/learn/login");

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers("/learn/login/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/learn/subjects/**", "/learn/sections/**", "/learn/articles/**", "/learn/users/**").hasAnyRole("STUDENT", "TEACHER");
        http.authorizeRequests().antMatchers("/learn/articles/**").hasRole("TEACHER");
        http.authorizeRequests().antMatchers("/learn/sections/**", "/learn/subjects/**", "/learn/articles/**", "/learn/users/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        /*
        http.csrf().disable();   // ENABLE
        // http.httpBasic().disable();
        // no session
        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // http.authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests().antMatchers("/learn/login").permitAll()
                .antMatchers("/learn/users/**").permitAll()
                        .antMatchers("/learn/subjects/**").hasRole("ADMIN")
                        //.antMatchers("/learn/users/**").hasRole("STUDENT")
                        .anyRequest().authenticated();
                        .and().apply(new JwtConfigurer(jwtTokenProvider));
         */

        /*
        http.authorizeRequests()
                // эти всем разрешены - тут url
                    .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/subject_images/**", "/article_images/**").permitAll()
                    .antMatchers("/learn", "/learn/login", "/learn/register").permitAll()
                .antMatchers("/learn/subjects/**").permitAll()
                .antMatchers("learn/subjects/**", "learn/sections/**").permitAll()
                // любой другой запрос (any) только для authenticated
                    //.anyRequest().authenticated()
                // а тут настройки для входа и выхода
                //.and().formLogin().loginPage("/learn/login").permitAll()
                .and().formLogin().loginPage("/learn/login").defaultSuccessUrl("/learn/home", true)
                .successHandler(SuccessLoginHandler)
                .and().logout().invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/learn/logout")).logoutSuccessUrl("/learn/logout-success").permitAll()
        ;
         */

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

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

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
     */
}
