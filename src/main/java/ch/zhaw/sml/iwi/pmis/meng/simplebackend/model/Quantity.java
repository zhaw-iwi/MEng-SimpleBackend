package ch.zhaw.sml.iwi.pmis.meng.simplebackend.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import lombok.Data;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType", visible = true)
@JsonSubTypes({ 
  @Type(value = QuantityPcs.class, name = "QuantityPcs"),
  @Type(value = QuantityTons.class, name = "QuantityTons"),
})
@Data
public abstract class Quantity implements Serializable{
    private int id;
}
