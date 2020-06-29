package ch.zhaw.sml.iwi.pmis.meng.simplebackend.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue("QuantityTons")
public class QuantityTons extends Quantity {
    private double amount;

    public QuantityTons() {
    }

    
    public QuantityTons(double amount) {
        this.amount = amount;
    }

    
}
