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

import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.Task;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.UserAccount;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.repository.TaskRepository;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.repository.UserAccountRepository;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
@Path("/task")
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserAccountRepository userRepository;

    @GET
    @CrossOrigin
    @Path("/unassigned")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getUnassigendTasks() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserAccount user = userRepository.findById(authentication.getName()).get();

        System.out.println("ch.zhaw.sml.iwi.pmis.meng.simplebackend.boundary.TaskService.getUnassigendTasks(): " + user.getLoginName());
        
        List<Task> resList = new ArrayList<>();
        for (Task t : taskRepository.findAll()) {
            resList.add(t);
        }
        return resList;
    }

}
