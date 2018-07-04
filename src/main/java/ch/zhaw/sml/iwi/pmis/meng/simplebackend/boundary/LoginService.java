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
package ch.zhaw.sml.iwi.pmis.meng.simplebackend.boundary;

import ch.zhaw.sml.iwi.pmis.gtd.lite.backend.dto.AuthorizationToken;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.UserAccount;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.repository.UserAccountRepository;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.security.JWTUtility;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author Peter Heinrich <peter.heinrich@zhaw.ch>
 */
@Component
@Path("/login")
public class LoginService {

    @Autowired
    private UserAccountRepository userRepository;

    @Autowired
    private JWTUtility jwtUtility;

    @GET
    @CrossOrigin
    @Produces(MediaType.APPLICATION_JSON)
    public Response login() {
        
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            UserAccount user = userRepository.findById(authentication.getName()).get();

            if (user == null) {
                // This shouldn't be reachable anyway as login() sould be protected
                // by HTTP-Basic authorization. Hence if successful, we should have
                // a valid user object here. 
                Response.status(Response.Status.UNAUTHORIZED).build();
            }

            AuthorizationToken token = jwtUtility.generate(authentication);
            return Response.ok(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    

}
