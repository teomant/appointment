package org.teomant.appointment.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.teomant.appointment.security.authFilter.AuthTokenFilter;
import org.teomant.appointment.security.service.UserDetailsServiceImpl;

@Configuration
@Order(1)
@RequiredArgsConstructor
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AuthTokenFilter tokenFilter = new AuthTokenFilter(userDetailsService);

        http.antMatcher("/api/**");

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/api/**")
                .hasAnyRole("CLIENT")
                .anyRequest()
                .permitAll()
                .and()
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .anonymous()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
