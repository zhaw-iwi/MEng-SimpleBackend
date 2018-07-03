package ch.zhaw.sml.iwi.pmis.meng.simplebackend.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType", visible = true)
@JsonSubTypes({ 
  @Type(value = QuantityPcs.class, name = "QuantityPcs"),
  @Type(value = QuantityTons.class, name = "QuantityTons"),
})
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "Quantity_Type")
public abstract class Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   
}
