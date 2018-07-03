package ch.zhaw.sml.iwi.pmis.meng.simplebackend.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Data
@Entity
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @Cascade(CascadeType.ALL)
    @OneToOne(fetch = FetchType.EAGER)
    private Quantity quantity;
    
    @Enumerated(EnumType.STRING)
    private EPartType type = EPartType.STANDARD;
    
    @Cascade(CascadeType.ALL)
    @OneToMany
    private List<Attribute> attributes = new ArrayList<>();
}
