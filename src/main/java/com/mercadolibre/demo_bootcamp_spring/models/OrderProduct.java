package com.mercadolibre.demo_bootcamp_spring.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="OrderProduct")
public class OrderProduct {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "productId")
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderId")
    private Orders orders;

    private Integer quantity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderProductId")
    private Integer orderProductId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProduct)) return false;
        OrderProduct that = (OrderProduct) o;
        return (orders.getOrderId() != null && that.orders.getOrderId() != null && Objects.equals(orders.getOrderId(), that.orders.getOrderId())) &&
                Objects.equals(product.getProductId(), that.product.getProductId()) &&
                Objects.equals(getQuantity(), that.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders != null ? orders.getOrderId() : null, product.getProductId(), quantity);
    }
}
