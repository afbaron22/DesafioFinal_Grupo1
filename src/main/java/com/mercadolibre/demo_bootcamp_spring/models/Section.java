package com.mercadolibre.demo_bootcamp_spring.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Stack;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Sections")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sectionId")
    private State state;
    private Integer warehouseCode;
    private Integer minSize;
    private Integer maxSize;
    private Integer batchQuantity;

    @OneToOne(mappedBy = "section")
    private InboundOrder inboundOrder;
}
