package ch.zhaw.sml.iwi.pmis.meng.simplebackend;

import ch.zhaw.sml.iwi.pmis.meng.simplebackend.boundary.InventoryService;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.Attribute;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.Part;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.QuantityTons;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.repository.InventoryRepository;
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
    private InventoryRepository inventoryRepository;

    public SimpleBackend() {
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        register(InventoryService.class);  
        register(CorsFilter.class);
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
            Part p = new Part();
            p.setName("Cola");
            p.setQuantity(new QuantityTons(25));
            Attribute a = new Attribute();
            a.setName("BottleMaterial");
            a.setValue("pp");
            p.getAttributes().add(a);

            a = new Attribute();
            a.setName("BottleColor");
            a.setValue("red");
            p.getAttributes().add(a);
            inventoryRepository.save(p);
            
            p = new Part();
            p.setName("Fanta");
            p.setQuantity(new QuantityTons(50));
            inventoryRepository.save(p);
            
            
        };
    }
    
}
