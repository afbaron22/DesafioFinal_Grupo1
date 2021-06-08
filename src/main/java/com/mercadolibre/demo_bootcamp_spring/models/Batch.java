package com.mercadolibre.demo_bootcamp_spring.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Batches")
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="batchNumber")
    private Integer batchNumber;

    @OneToOne()
    @JoinColumn(name = "FK_PRODUCT", updatable = false, nullable = false)
    private Product product;

    private Float currentTemperature;
    private Float minimumTemperature;
    private String dueDate;
    private String manufacturingDate;
    private String manufacturingTime;
    private Integer initialQuantity;
    private Integer currentQuantity;

}
