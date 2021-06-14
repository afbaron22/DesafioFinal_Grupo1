package com.example.demo2.repository;

import com.example.demo2.models.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch,Integer> {
    @Query("SELECT u FROM Batch u WHERE u.inboundOrder.orderNumber = ?1")
    Optional<List<Batch>> findByInboundOrder(String idInbound);

    @Query("SELECT u FROM Batch u WHERE u.product.productId = ?1")
    Optional<List<Batch>> findByProductId(String idInbound);

    @Query("SELECT u.inboundOrder.section.warehouseCode,sum(u.currentQuantity) FROM Batch u " +
            "WHERE u.product.productId = ?1 group by u.inboundOrder.section.warehouseCode")
    Optional<List<Object[]>> findWarehousesWithProduct(String idInbound);
}
