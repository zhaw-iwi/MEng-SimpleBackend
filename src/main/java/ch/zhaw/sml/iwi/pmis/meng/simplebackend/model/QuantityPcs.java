package ch.zhaw.sml.iwi.pmis.meng.simplebackend.model;

import lombok.Data;

@Data
public class QuantityPcs extends Quantity {
    private int amount;

    public QuantityPcs() {
    }

    
    public QuantityPcs(int amount) {
        this.amount = amount;
    }

    
    
    
}
