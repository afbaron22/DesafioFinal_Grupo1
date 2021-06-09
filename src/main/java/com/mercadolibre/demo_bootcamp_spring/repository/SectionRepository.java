package com.mercadolibre.demo_bootcamp_spring.repository;

import com.mercadolibre.demo_bootcamp_spring.models.Section;
import com.mercadolibre.demo_bootcamp_spring.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, State> {
}
