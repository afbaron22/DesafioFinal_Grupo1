package com.example.demo_bootcamp_spring.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "FK_INBOUNDORDER", updatable = false, nullable = true)
    private InboundOrder inboundOrder;

}
