package com.example.demo_bootcamp_spring.repository;

import com.example.demo_bootcamp_spring.models.Batch;
import com.example.demo_bootcamp_spring.models.Product;
import com.example.demo_bootcamp_spring.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
    @Query("SELECT u FROM Batch u WHERE u.inboundOrder.orderNumber = ?1")
    Optional<List<Batch>> findByInboundOrder(String idInbound);

    @Query("SELECT u FROM Batch u WHERE u.product.productId = ?1")
    Optional<List<Batch>> findByProductId(String idProduct);

    @Query("SELECT u.inboundOrder.section.warehouseCode,sum(u.currentQuantity) FROM Batch u " +
            "WHERE u.product.productId = ?1 group by u.inboundOrder.section.warehouseCode")
    //Lista  de objetos {WarehouseCode,currentQuantity}
    Optional<List<Object[]>> findWarehousesWithProduct(String idProduct);


    @Query("select b from Batch b join b.product p where p.productId = :productId and b.dueDate > :dueDate")
    List<Batch> findByProductIdAndDueDate(
            @Param("productId")String productId, @Param("dueDate") LocalDate dueDate);

    @Query("SELECT u.product FROM Batch u WHERE u.product.state = :state")
    Optional<List<Product>> findProduct(@Param("state") State state);

    /*@Query("SELECT u FROM Batch u WHERE u.inboundOrder.section.warehouseCode = :idWarehouse")
    Optional<List<Batch>> findProductDueDate(@Param("idWarehouse") String idWarehouse);
*/
}
