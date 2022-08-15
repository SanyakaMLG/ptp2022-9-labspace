package com.example.springdemo.config;

import com.example.springdemo.exceptions.CustomAccessDeniedHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers( "/auth/**").not().fullyAuthenticated()
                    //.antMatchers( "/auth/register", "/auth/activate/*", "/auth/forgot_password", "/auth/reset_password**", "/auth/login").not().fullyAuthenticated()
                    .antMatchers("/admin/**").hasRole("ADMIN") //здесь прописать доступ для админа
                    .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") // тут - для юзера
                    //.antMatchers( "/index", "/minor", "/lab**").authenticated()
                    .antMatchers( "/main/**").authenticated()
                    .antMatchers( "/error").permitAll()
                .and()
                    .exceptionHandling().accessDeniedPage("/accessDenied.html")
                .and()
                    .formLogin()
                    .loginPage("/auth/login")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/", true)
                    .permitAll()
                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessUrl("/auth/login")
                .and()
                    .cors()
                .and()
                    .csrf()
                    .disable();

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/css/**", "/fonts/**", "/font-awesome/**", "/js/**",
                        "/img/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder)
                .usersByUsernameQuery("select email,password,active from users where email=?")
                .authoritiesByUsernameQuery("select users.email,role.roles from users  inner join role  on users.user_id=role.user_id where users.email=?");
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @ExceptionHandler(Throwable.class)
    public String handleAnyException(Throwable ex, HttpServletRequest request) {
        return ClassUtils.getShortName(ex.getClass());
    }

}

