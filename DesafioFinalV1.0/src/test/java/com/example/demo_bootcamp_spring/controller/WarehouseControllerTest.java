package com.example.demo_bootcamp_spring.controller;

import com.example.demo_bootcamp_spring.dtos.BatchStockProduct;
import com.example.demo_bootcamp_spring.dtos.BatchStockProductSearch;
import com.example.demo_bootcamp_spring.dtos.BatchStockWareHouse;
import com.example.demo_bootcamp_spring.dtos.SectionDTO;
import com.example.demo_bootcamp_spring.models.*;
import com.example.demo_bootcamp_spring.repository.BatchRepository;
import com.example.demo_bootcamp_spring.repository.SectionRepository;
import com.example.demo_bootcamp_spring.services.Batch.BatchService;
import com.example.demo_bootcamp_spring.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.http.HttpHeaders;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "BUYER")
class WarehouseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BatchRepository batchRepository;
    @MockBean
    private SectionRepository sectionRepository;
    @MockBean
    private BatchService batchService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void shouldGetBatchesInWarehouseByDueDate() throws Exception {
        ObjectMapper mapper = makeMapper();
        BatchStockWareHouse listBatchExpected = createBatchStockWareHouse();
        List<Batch> listBatch = createBatchListFS();
        when(batchRepository.findProductDueDate("60")).thenReturn(Optional.of(listBatch));
        when(jwtTokenUtil.getUsernameFromToken("token")).thenReturn("testUsername");
        when(batchService.validate("testUsername")).thenReturn(60);
        when(batchService.getBatchesInWarehouseByDueDate(60,27,"FS","asc")).thenReturn(listBatchExpected);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/due-date")
                    .header("Authorization","Bearer token")
                    .param("days","27")
                    .param("category","FS")
                    .param("order", "asc"))
                    .andDo(print())
                    .andExpect(content().string(mapper.writeValueAsString(listBatchExpected)));
    }

    private static ObjectMapper makeMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    private BatchStockWareHouse createBatchStockWareHouse(){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> mapBatch = new HashMap<>();
        mapBatch.put("batchNumber","1");
        mapBatch.put("productId","1");
        mapBatch.put("dueDate", LocalDate.of(2021,7,12));
        mapBatch.put("quantity",300);
        Map<String, Object> mapBatch2 = new HashMap<>();
        mapBatch2.put("batchNumber","2");
        mapBatch2.put("productId","1");
        mapBatch2.put("dueDate",LocalDate.of(2021,7,13));
        mapBatch2.put("quantity",400);
        list.add(mapBatch);
        list.add(mapBatch2);
        return new BatchStockWareHouse(list);
    }

    private Product createProductFS(){
        return new Product("1","productName","test", State.FS, 4000.0);
    }

    private InboundOrder createInboundOrderFS(){
        LocalDate date = LocalDate.of(2021,5,14);
        return new InboundOrder("numberTest",date, createSectionFS());
    }

    private Section createSectionFS(){
        return new Section("sectionTest",State.FS,"testWHC",20,20,500);
    }

    private List<Batch> createBatchListFS(){
        Product product = createProductFS();
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2021,5,14), LocalTime.of(1,1,1));
        LocalDate expirationDate = LocalDate.of(2021,7,14);
        LocalDate expirationDate1 = LocalDate.of(2021,7,13);
        LocalDate expirationDate2 = LocalDate.of(2021,7,12);
        LocalDate date = LocalDate.of(2021,5,14);
        LocalDate date1 = LocalDate.of(2021,5,14);
        LocalDate date2 = LocalDate.of(2021,5,14);
        List<Batch> batchList = new ArrayList<>();
        batchList.add(new Batch("0",product,(float)20,(float)20,expirationDate,date,dateTime,500,500, createInboundOrderFS()));
        batchList.add(new Batch("1",product,(float)20,(float)20,expirationDate2,date1,dateTime,500,300, createInboundOrderFS()));
        batchList.add(new Batch("2",product,(float)20,(float)20,expirationDate1,date2,dateTime,500,400, createInboundOrderFS()));
        return batchList;
    }

    private SectionDTO createSectionDto(){
        return new SectionDTO(State.FS,"testWHC");
    }

    private List<BatchStockProduct> createBatchStockProduct(){
        List<BatchStockProduct> listFound = new ArrayList<>();
        LocalDate date = LocalDate.of(2021,7,14);
        LocalDate date1 = LocalDate.of(2021,7,13);
        LocalDate date2 = LocalDate.of(2021,7,12);
        listFound.add(new BatchStockProduct("0",500,date));
        listFound.add(new BatchStockProduct("1",300,date2));
        listFound.add(new BatchStockProduct("2",400,date1));
        return listFound;
    }

    private BatchStockProductSearch createBatchStockProductSearch(){
        SectionDTO sectionDto = createSectionDto();
        List<BatchStockProduct> listFound = createBatchStockProduct();
        return new BatchStockProductSearch(sectionDto,"productTest",listFound);
    }

}