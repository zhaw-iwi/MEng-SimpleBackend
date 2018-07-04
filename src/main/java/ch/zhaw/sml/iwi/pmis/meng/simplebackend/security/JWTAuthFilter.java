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

import ch.zhaw.sml.iwi.pmis.meng.simplebackend.security.UserDetailService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Peter Heinrich <peter.heinrich@zhaw.ch>
 */
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(JWTAuthFilter.class.getName());

    @Autowired
    private JWTUtility tokenUtility;

    @Autowired
    private UserDetailService userDetailsService;

    @SuppressWarnings("UseSpecificCatch")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // First thing to check: Is there a non-empty authtoken present?
            String bearerToken = ((HttpServletRequest) request).getHeader("Authorization");
            if (bearerToken != null && !bearerToken.isEmpty() && bearerToken.startsWith("Bearer")) {
                // Striping away the "Bearer " ...
                bearerToken = bearerToken.substring(7, bearerToken.length());

                // Trying to validate the token. It'l throw a RuntimeExpetion
                // if parsing or validating fails.
                tokenUtility.validate(bearerToken);
                SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
                String loginEmail = tokenUtility.getLoginEmail(bearerToken);
                List<GrantedAuthority> authorities = tokenUtility.getUserGrantedAuthorititesByToken(bearerToken);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginEmail, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOGGER.log(Level.INFO, "Granted access by token to {0}", loginEmail);
                // Let's invoke the rest of the filter-stack.
                filterChain.doFilter(request, response);
            }
            else {
                // This is not a JWT authorization attempt. Hence we continue 
                // filtering.
                filterChain.doFilter(request, response);
            }
        } catch (RuntimeException e) {
            // There was an unsuccessfull token-based authorization attempt. 
            // Thus we stop filtering and return 401.
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

}
