package ch.zhaw.sml.iwi.pmis.meng.simplebackend.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue("QuantityPcs")
public class QuantityPcs extends Quantity {
    private int amount;

    public QuantityPcs() {
    }

    
    public QuantityPcs(int amount) {
        this.amount = amount;
    }

    
    
    
}
