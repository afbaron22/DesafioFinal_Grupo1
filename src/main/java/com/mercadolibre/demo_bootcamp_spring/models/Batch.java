package com.mercadolibre.demo_bootcamp_spring.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Batches")
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="batchNumber")
    private String batchNumber;

    @ManyToOne
    @JoinColumn(name = "FK_PRODUCT", updatable = false, nullable = false)
    private Product product;

    private Float currentTemperature;

    private Float minimumTemperature;

    private LocalDate dueDate;

    private LocalDate manufacturingDate;

    private LocalDateTime manufacturingTime;

    private Integer initialQuantity;

    private Integer currentQuantity;

    @ManyToOne
    @JoinColumn(name = "FK_INBOUNDORDER", updatable = false, nullable = false)
    private InboundOrder inboundOrder;

    public Batch(String batchNumber, Product product, Float currentTemperature, Float minimumTemperature, LocalDate dueDate, LocalDate manufacturingDate, LocalDateTime manufacturingTime, Integer initialQuantity, Integer currentQuantity) {
        this.batchNumber = batchNumber;
        this.product = product;
        this.currentTemperature = currentTemperature;
        this.minimumTemperature = minimumTemperature;
        this.dueDate = dueDate;
        this.manufacturingDate = manufacturingDate;
        this.manufacturingTime = manufacturingTime;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
    }
}
