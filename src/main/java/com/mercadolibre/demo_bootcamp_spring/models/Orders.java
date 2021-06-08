package com.mercadolibre.demo_bootcamp_spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderId")
    private Integer orderId;
    private String user;
    private String createdAt;

    @JoinTable(
            name = "rel_order_products",
            joinColumns = @JoinColumn(name = "FK_ORDER", nullable = false),
            inverseJoinColumns = @JoinColumn(name="FK_PRODUCT", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Product> products;

    private InetAddress getUserAdd() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("google.com", 80));
        return socket.getLocalAddress();
    }

    private String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now).toString();

    }
}
