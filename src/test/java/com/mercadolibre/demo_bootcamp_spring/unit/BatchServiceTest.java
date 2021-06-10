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







}
