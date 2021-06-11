package com.mercadolibre.demo_bootcamp_spring.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="OrderProduct")
public class OrderProduct {

    @ManyToOne()
    @JoinColumn(name = "productId")
    private Product product;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "orderId")
    private Orders orders;

    private Integer quantity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderProductId")
    private Integer orderProductId;


}
