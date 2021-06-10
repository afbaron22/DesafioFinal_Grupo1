package com.mercadolibre.demo_bootcamp_spring.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercadolibre.demo_bootcamp_spring.dtos.BatchDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDate;
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
    private String orderNumber;
    private LocalDate orderDate;

    @OneToOne()
    @JoinColumn(name = "FK_SECTION", updatable = false, nullable = false)
    private Section section;
}
