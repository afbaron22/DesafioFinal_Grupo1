package com.example.demo_bootcamp_spring.repository;

import com.example.demo_bootcamp_spring.models.InboundOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InboundOrderRepository extends JpaRepository<InboundOrder,String> {


}
