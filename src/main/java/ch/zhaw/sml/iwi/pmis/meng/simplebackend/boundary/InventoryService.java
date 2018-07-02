package ch.zhaw.sml.iwi.pmis.meng.simplebackend.boundary;

import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.Part;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.repository.InventoryRepository;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;

@Path("/inventory")
public class InventoryService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Part> getAllParts() {
        List<Part> resList = new ArrayList<>();
        for(String s : InventoryRepository.getInstance().getParts().keySet()) {
            resList.add(InventoryRepository.getInstance().getParts().get(s));
        }
        return resList;
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Part getEntry(@PathParam("id") String identString) {        
        return InventoryRepository.getInstance().getParts().get(identString);
    }
   
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEntry(Part part, @PathParam("id") String identString) {  
        part.setName(identString);
        InventoryRepository.getInstance().getParts().put(identString, part);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addEntry(Part part) {        
        InventoryRepository.getInstance().getParts().put(part.getName(), part);
    }
    
    @DELETE
    @Path("/{id}")
    public void updateEntry(@PathParam("id") String identString) {        
        InventoryRepository.getInstance().getParts().remove(identString);
    }

}
