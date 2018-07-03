package ch.zhaw.sml.iwi.pmis.meng.simplebackend.boundary;

import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.Part;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.repository.InventoryRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Path("/inventory")
@Transactional
@Controller
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Part> getAllParts() {
        List<Part> resList = new ArrayList<>();
        for(Part p : inventoryRepository.findAll()) {
            Hibernate.initialize(p.getAttributes());
            // p.getAttributes().size();
            resList.add(p);
        }
        return resList;
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Part getEntry(@PathParam("id") Long id) {        
        return inventoryRepository.findById(id).get();
    }
   
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEntry(Part part, @PathParam("id") Long id) {  
        part.setId(id);
        inventoryRepository.save(part);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addEntry(Part part) {
        part.setId(null);
        inventoryRepository.save(part);
    }
    
    @DELETE
    @Path("/{id}")
    public void updateEntry(@PathParam("id") Long id) {        
        inventoryRepository.deleteById(id);
    }

    @GET
    @Path("/min")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Part> getMinAmout() {  
        List<Part> resList = new ArrayList<>();
        for(Part p : inventoryRepository.getPartByQuantityMinTons(40)) {
            resList.add(p);
        }
        return resList;
    }
}
