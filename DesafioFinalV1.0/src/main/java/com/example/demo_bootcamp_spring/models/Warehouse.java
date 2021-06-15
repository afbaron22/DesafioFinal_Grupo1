package com.example.demo_bootcamp_spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Warehouses")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idWarehouse")
    private Integer idWarehouse;
    private String address;
    private String location;
    private String province;

    @OneToOne(mappedBy = "Warehouses")
    private Account account;

/*
    @JoinTable(
            name = "rel_warehouse_products",
            joinColumns = @JoinColumn(name = "FK_WAREHOUSE", nullable = false),
            inverseJoinColumns = @JoinColumn(name="FK_PRODUCT", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
*/

}
