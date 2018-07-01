package ch.zhaw.sml.iwi.pmis.meng.simplebackend.repository;

import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.Part;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.QuantityPcs;
import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.QuantityTons;
import java.util.HashMap;
import java.util.Map;

public class InventoryRepository {
    
    private static InventoryRepository instance;
    
    public static InventoryRepository getInstance() {
        if(instance == null) {
            instance = new InventoryRepository();
            populateDemoData();
        }
        return instance;
    }

    private static void populateDemoData() {
        Part p = new Part();
        p.setName("Concrete");
        p.setQuantity(new QuantityTons(1.0));
        instance.getParts().put(p.getName(),p);
        
        p = new Part();
        p.setName("SteelBeam");
        p.setQuantity(new QuantityPcs(5));
        instance.getParts().put(p.getName(),p);

    }
    
    private Map<String,Part> parts = new HashMap<>();

    public Map<String,Part> getParts() {
        return parts;
    }

    
}
