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

import ch.zhaw.sml.iwi.pmis.gtd.lite.backend.dto.AuthorizationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 *
 * @author Peter Heinrich <peter.heinrich@zhaw.ch>
 */
@Component
public class JWTUtility {

    private static final Logger LOGGER = Logger.getLogger(JWTUtility.class.getName());
    
    @Value("${config.jwt.tokenExpiration}")
    private int tokenExpiration;
    @Value("${config.jwt.tokenSecret}")
    private String tokenSecret;

    public AuthorizationToken generate(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        JwtBuilder builder = Jwts.builder();

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authentication.getAuthorities());

        Date expiresAt = new Date(new Date().getTime() + tokenExpiration);
        String token = builder
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
        LOGGER.log(Level.INFO, "Created JWT-Token for {0}. Valid until {1}", new Object[]{user.getUsername(), expiresAt});
        
        AuthorizationToken authToken = new AuthorizationToken();
        authToken.setExpiresAt(expiresAt);
        authToken.setToken(token);
        return authToken;
    }

    public void validate(String token) {
           Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token);
    }

    public String getLoginEmail(String token) {
        return Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody().getSubject();
    }
    public List<GrantedAuthority> getUserGrantedAuthorititesByToken(String bearerToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(bearerToken);
        ArrayList<HashMap> roles = (ArrayList) claims.getBody().get("authorities");
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList();
        roles.forEach((roleMap) -> {
            authorities.add((GrantedAuthority) () -> roleMap.get("authority").toString());
        });
        return authorities;
    }

}
