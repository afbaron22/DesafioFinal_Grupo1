package com.example.demo_bootcamp_spring.controller;
import com.example.demo_bootcamp_spring.dtos.BatchStockProduct;
import com.example.demo_bootcamp_spring.dtos.BatchStockProductSearch;
import com.example.demo_bootcamp_spring.dtos.SectionDTO;
import com.example.demo_bootcamp_spring.models.*;
import com.example.demo_bootcamp_spring.repository.BatchRepository;
import com.example.demo_bootcamp_spring.repository.SectionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "BUYER")
class BatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BatchRepository batchRepository;

    @MockBean
    private SectionRepository sectionRepository;

    @Test
    public void shouldGetProductFromBatches() throws Exception {
        final ObjectMapper mapper = makeMapper();
        BatchStockProductSearch expected = createBatchStockProductSearch();
        List<Batch> batchList = createBatchList();
        when(batchRepository.findByProductId("productTest")).thenReturn(Optional.of(batchList));
        when(sectionRepository.findById("sectionTest")).thenReturn(Optional.of(createSection()));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/list").param("querytype", "productTest"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().string(mapper.writeValueAsString(expected)));
    }

    private static ObjectMapper makeMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    private Product createProduct(){
        return new Product("productTest","productName","test", State.FS);
    }

    private InboundOrder createInboundOrder(){
        LocalDate date = LocalDate.of(2021,5,14);
        return new InboundOrder("numberTest",date,createSection());
    }

    private Section createSection(){
        return new Section("sectionTest",State.FS,"testWHC",20,20,500);
    }

    private List<Batch> createBatchList(){
        Product product = createProduct();
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2021,5,14), LocalTime.of(1,1,1));
        LocalDate expirationDate = LocalDate.of(2021,7,14);
        LocalDate expirationDate1 = LocalDate.of(2021,7,14);
        LocalDate expirationDate2 = LocalDate.of(2021,7,14);
        LocalDate date = LocalDate.of(2021,5,14);
        LocalDate date1 = LocalDate.of(2021,5,14);
        LocalDate date2 = LocalDate.of(2021,5,14);
        List<Batch> batchList = new ArrayList<>();
        batchList.add(new Batch("test",product,(float)20,(float)20,expirationDate,date,dateTime,500,500,createInboundOrder()));
        batchList.add(new Batch("test1",product,(float)20,(float)20,expirationDate1,date1,dateTime,500,300,createInboundOrder()));
        batchList.add(new Batch("test2",product,(float)20,(float)20,expirationDate2,date2,dateTime,500,400,createInboundOrder()));
        return batchList;
    }

    private List<BatchStockProduct> createBatchStockProduct(){
        List<BatchStockProduct> listFound = new ArrayList<>();
        LocalDate date = LocalDate.of(2021,7,14);
        LocalDate date1 = LocalDate.of(2021,7,14);
        LocalDate date2 = LocalDate.of(2021,7,14);
        listFound.add(new BatchStockProduct("test",500,date));
        listFound.add(new BatchStockProduct("test1",300,date1));
        listFound.add(new BatchStockProduct("test2",400,date2));
        return listFound;
    }

    private SectionDTO createSectionDto(){
        return new SectionDTO(State.FS,"testWHC");
    }

    private BatchStockProductSearch createBatchStockProductSearch(){
        SectionDTO sectionDto = createSectionDto();
        List<BatchStockProduct> listFound = createBatchStockProduct();
        return new BatchStockProductSearch(sectionDto,"productTest",listFound);
    }

}