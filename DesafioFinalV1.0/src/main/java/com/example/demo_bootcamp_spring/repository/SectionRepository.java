package com.example.demo_bootcamp_spring.repository;

import com.example.demo_bootcamp_spring.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, String> {
}
