package ch.zhaw.sml.iwi.pmis.meng.simplebackend.model;

import lombok.Data;

@Data
public class QuantityTons extends Quantity {
    private double amount;

    public QuantityTons() {
    }

    
    public QuantityTons(double amount) {
        this.amount = amount;
    }

    
}
