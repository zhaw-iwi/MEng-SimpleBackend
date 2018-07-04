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
package ch.zhaw.sml.iwi.pmis.meng.simplebackend.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OrderColumn;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Peter Heinrich <peter.heinrich@zhaw.ch>
 */
@Entity
@Data
@SuppressWarnings("SerializableClass")
public class UserAccount {
    
    @Id
    private String loginName;
    
    private String passwordHash;
    
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @OrderColumn(name="RoleOrder")
    private UserRole[] roles = {UserRole.ROLE_USER};
    
    public void setAndHashPassword(String plainTextPW) {
        passwordHash = (new BCryptPasswordEncoder()).encode(plainTextPW);
    }
}
