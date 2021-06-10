package com.mercadolibre.demo_bootcamp_spring.unit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mercadolibre.demo_bootcamp_spring.dtos.BatchDTO;
import com.mercadolibre.demo_bootcamp_spring.dtos.BatchStock;
import com.mercadolibre.demo_bootcamp_spring.dtos.InboundOrderDTO;
import com.mercadolibre.demo_bootcamp_spring.dtos.SectionDTO;
import com.mercadolibre.demo_bootcamp_spring.models.*;
import com.mercadolibre.demo_bootcamp_spring.repository.ProductsRepository;
import com.mercadolibre.demo_bootcamp_spring.services.Batch.BatchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringBootTest
public class BatchServiceTest {

    @Mock
    ProductsRepository productsRepository;

    @InjectMocks
    BatchService service;

    @BeforeEach
    void setUp() {
        LocalDate date = LocalDate.parse("2018-02-27");
        var section = new Section("1",State.FF,"1",0,0,5);
        var sectionDto = new SectionDTO(State.FF,"1");
        var batchStock = new BatchDTO("1","1",23F,18F,1,3,LocalDate.parse("2018-02-27"),LocalDateTime.parse("2018-02-27 18:14:01"),LocalDate.parse("2018-02-27 18:14:01"));
        var batchStock2 = new BatchDTO("1","1",23F,18F,1,3,LocalDate.parse("2018-02-27"),LocalDateTime.parse("2018-02-27 18:14:01"),LocalDate.parse("2018-02-27 18:14:01"));
        var res = List.of(batchStock,batchStock2);
        Product product = new Product("1","Manzanas Colombianas","Rojas",State.FF);

    }

    @Test
    void testReturnBatch() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        var sectionDto = new SectionDTO(State.FF,"1");
        var batchStock = new BatchDTO("1","1",23F,18F,1,3,LocalDate.parse("2018-02-27"),LocalDateTime.parse("2018-02-27",df),LocalDate.parse("2018-02-27"));
        var batchStock2 = new BatchDTO("2","1",23F,18F,1,3,LocalDate.parse("2018-02-27"),LocalDateTime.parse("2018-02-27",df),LocalDate.parse("2018-02-27"));    var inboundOrderDto = new InboundOrderDTO(1,LocalDate.parse("2018-02-27"),sectionDto,List.of(batchStock,batchStock2));
        var size = service.getBatchSize(inboundOrderDto);
        Assertions.assertEquals(2,size);

    }
    private Integer getBatchSize(InboundOrderDTO inboundOrderDTO){
        return  inboundOrderDTO
                .getBatchStock().stream()
                .map(x-> {
                    return x.getCurrentQuantity();
                }).collect(Collectors.toList()).stream().reduce(0,(a, b)->a+b);
    }




}
