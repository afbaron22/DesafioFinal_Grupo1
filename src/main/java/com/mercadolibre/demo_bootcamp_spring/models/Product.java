package com.mercadolibre.demo_bootcamp_spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="productId")
    private Integer productId;
    private String name;
    private String additionalInfo;
    private State state;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Batch batch;

}
