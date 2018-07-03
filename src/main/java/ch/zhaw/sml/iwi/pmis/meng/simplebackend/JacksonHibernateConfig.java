/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.sml.iwi.pmis.meng.simplebackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;


// Based on https://javaeeblog.wordpress.com/2016/02/02/restful-lazy-load-mit-jackson-und-hibernate/
@Provider
//@Produces(MediaType.APPLICATION_JSON)
public class JacksonHibernateConfig implements ContextResolver<ObjectMapper> {
    
    private ObjectMapper objectMapper;

    public JacksonHibernateConfig() {
        objectMapper = new ObjectMapper();
        Hibernate5Module h5m = new Hibernate5Module();
        objectMapper.registerModule(h5m);
    }
    
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }
 
   
}
