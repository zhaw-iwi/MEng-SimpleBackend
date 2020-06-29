 package ch.zhaw.sml.iwi.pmis.meng.simplebackend.repository;

import ch.zhaw.sml.iwi.pmis.meng.simplebackend.model.Part;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Part, Long> {
    
    @Query("SELECT p FROM Part p join p.quantity q WHERE TYPE(q) IN (QuantityTons) AND q.amount > ?1")
    List<Part> getPartByQuantityMinTons(double amout);

}
