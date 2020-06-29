package ch.zhaw.sml.iwi.pmis.meng.simplebackend.boundary;

import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.Part;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.repository.InventoryRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    

    @GetMapping(path = "/hallo")
    public String getHello() {
        
        return "Hallo";
    }

    @GetMapping(path = "/inventory")
    public List<Part> getAllParts() {
        List<Part> resList = new ArrayList<>();
        for(Part p : inventoryRepository.findAll()) {
            Hibernate.initialize(p.getAttributes());
            // p.getAttributes().size();
            resList.add(p);
        }
        return resList;
    }
    
    @GetMapping(path = "/inventory/{id}")
    public Part getEntry(@RequestParam("id") Long id) {        
        return inventoryRepository.findById(id).get();
    }
   
    @PutMapping(path = "/inventory/{id}")
    public void updateEntry(Part part, @RequestParam("id") Long id) {  
        part.setId(id);
        inventoryRepository.save(part);
    }

    @PostMapping(path = "/inventory")
    public void addEntry(Part part) {
        part.setId(null);
        inventoryRepository.save(part);
    }
    
    @PostMapping(path = "/inventory/{id}")
    public void updateEntry(@RequestParam("id") Long id) {        
        inventoryRepository.deleteById(id);
    }

    @GetMapping(path = "/inventory/min")
    public List<Part> getMinAmout() {  
        List<Part> resList = new ArrayList<>();
        for(Part p : inventoryRepository.getPartByQuantityMinTons(40)) {
            resList.add(p);
        }
        return resList;
    }
}
