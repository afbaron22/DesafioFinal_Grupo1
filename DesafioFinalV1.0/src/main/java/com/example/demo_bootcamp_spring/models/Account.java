package com.example.demo_bootcamp_spring.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String username;
    @Column
    @JsonIgnore
    private String password;
    @Column
    private String role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="warehouse_id", referencedColumnName = "idWarehouse")
    private Warehouse warehouse;


}
