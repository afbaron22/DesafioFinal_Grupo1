package com.mercadolibre.demo_bootcamp_spring.repository;

import com.mercadolibre.demo_bootcamp_spring.models.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch,Integer> {

    @Query("select b from Batch b join b.product p where p.productid = :productId and b.dueDate > :dueDate")
    List<Batch> findByProductIdAndDueDate(
            @Param("productId")String productId, @Param("dueDate")  Date dueDate);
}
