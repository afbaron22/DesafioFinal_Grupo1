package com.mercadolibre.demo_bootcamp_spring.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="InboundOrders")
public class InboundOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderNumber")
    private Integer orderNumber;
    private String orderDate;

    @OneToOne()
    @JoinColumn(name = "FK_SECTION", updatable = false, nullable = false)
    private Section section;

    @OneToMany(mappedBy = "batchNumber")
    private List<Batch> batches;

}
