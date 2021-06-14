package com.example.demo2.repository;

import com.example.demo2.models.InboundOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InboundOrderRepository extends JpaRepository<InboundOrder,String> {


}
