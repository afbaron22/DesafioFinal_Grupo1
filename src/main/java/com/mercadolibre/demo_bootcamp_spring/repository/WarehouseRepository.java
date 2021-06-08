package com.mercadolibre.demo_bootcamp_spring.repository;

import com.mercadolibre.demo_bootcamp_spring.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Integer> {

}
