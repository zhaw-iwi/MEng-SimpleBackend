package ch.zhaw.sml.iwi.pmis.meng.simplebackend.model;

import lombok.Data;

@Data
public class Part {
    private String name;
    private Quantity quantity;
    private EPartType type = EPartType.STANDARD;
}
