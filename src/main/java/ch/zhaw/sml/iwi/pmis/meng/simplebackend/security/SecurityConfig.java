/* 
 * Copyright 2018 Peter Heinrich <peter.heinrich@zhaw.ch>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.zhaw.sml.iwi.pmis.meng.simplebackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author Peter Heinrich <peter.heinrich@zhaw.ch>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${config.security.permitUnauthorizedConsoleAccess:false}")
    private boolean anonymousConsoleAccessEnabled;

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Bean
    public JWTAuthFilter jwtAuthenticationFilter() {
        return new JWTAuthFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        // Here we provide Spring-Security our way of accessing user credentials.
        authenticationManagerBuilder
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Always use a proper password hashing algorithm!
        // BCrypt is one ...
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();

        // We don't want ANY sessions at all. We are stateless!
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Enable CORS preflight OPTION requests without authentication
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

        // Enable Access to H2 console
        if (anonymousConsoleAccessEnabled) {
            http.authorizeRequests().antMatchers("/console").permitAll();
            http.authorizeRequests().antMatchers("/console/**").permitAll();
        }

        // Ensure that every other request MUST be authorized!
        http.authorizeRequests().anyRequest().authenticated();

        // Enable HTTP basic auth
        // HTTP basic auth as fallback authenticator for everything.
        // If something goes wrong here, we simply answer 401
        http.httpBasic().authenticationEntryPoint(unauthorizedEntryPoint);

        // H2 Console needs frame options to be disables to work.
        if (anonymousConsoleAccessEnabled) {
            http.headers().frameOptions().disable();
        }

        // Add our own JWT filter implementation to the filter stack
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
