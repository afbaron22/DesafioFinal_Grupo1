package com.example.demo_bootcamp_spring.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Stack;
@Entity
@Data

@NoArgsConstructor
@Table(name="Sections")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sectionId")
    private String id;
    private State state;
    private String warehouseCode;
    private Integer minSize;
    private Integer maxSize;
    private Integer batchQuantity;

    public Section(String id,State state, String warehouseCode, Integer minSize, Integer maxSize, Integer batchQuantity) {
        this.id = id;
        this.state = state;
        this.warehouseCode = warehouseCode;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.batchQuantity = batchQuantity;

    }


}
