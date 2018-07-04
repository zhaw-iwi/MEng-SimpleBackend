package ch.zhaw.sml.iwi.pmis.meng.simplebackend;

import ch.zhaw.sml.iwi.pmis.meng.simplebackend.boundary.LoginService;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.boundary.TaskService;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.Task;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.UserAccount;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.UserRole;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.repository.TaskRepository;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.repository.UserAccountRepository;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.security.CORSFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class SimpleBackend extends ResourceConfig {
  public static void main(String[] args) {
        SpringApplication.run(SimpleBackend.class, args);
    }
  
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private UserAccountRepository userRepository;

    public SimpleBackend() {
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        register(TaskService.class);
        register(LoginService.class);
        register(CORSFilter.class);
        register(JacksonHibernateConfig.class);
    }
    
    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            UserAccount u = new UserAccount();
            u.setLoginName("test");
            u.setAndHashPassword("test");
            u.setRoles(new UserRole[]{UserRole.ROLE_USER, UserRole.ROLE_ADMIN});
            userRepository.save(u);
            
            Task t = new Task();
            t.setName("Mudding");
            taskRepository.save(t);
            t = new Task();
            t.setName("Pudding");
            taskRepository.save(t);
            
        };
    }
    
}
