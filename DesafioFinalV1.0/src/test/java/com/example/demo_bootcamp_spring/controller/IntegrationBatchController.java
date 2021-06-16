package com.example.demo_bootcamp_spring.controller;

import com.example.demo_bootcamp_spring.dtos.*;
import com.example.demo_bootcamp_spring.exceptions.ExistingInboundOrderId;
import com.example.demo_bootcamp_spring.models.State;
import com.example.demo_bootcamp_spring.services.Batch.BatchService;
import com.example.demo_bootcamp_spring.services.JwtUserDetailsService;
import com.example.demo_bootcamp_spring.util.JwtTokenUtil;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "REPRESENTATIVE")
public class IntegrationBatchController {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private JwtUserDetailsService userDetailsService;

    @MockBean
    private BatchService batchService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    private InboundOrderTransactionRequest inboundOrderTransactionRequest;

    @BeforeEach
    public void setUp() {
        inboundOrderTransactionRequest = createInboundOrderTransactionRequest();
        jwtTokenUtil = new JwtTokenUtil("secret");
    }

    //------------------------------------------TESTS POST METHOD SAVEBATCH--------------------------------------------------
    @Test
    void testInsertBatch_whenReceiveAInboundOrderTransactionOK_thenReturnOkWithBatchStock() throws Exception {
        BatchStock batchStockResponse = createBatchStock();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user","password", AuthorityUtils.createAuthorityList("REPRESENTATIVE"));
        when(batchService.saveBatch(any())).thenReturn(batchStockResponse);
        when(batchService.validate(any())).thenReturn(1);

        this.mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization",jwtTokenUtil.generateToken(userDetails))
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.batchStock.[0].batchNumber").value("1"))
                .andExpect(jsonPath("$.batchStock.[0].productId").value("1"))
                .andExpect(jsonPath("$.batchStock.[0].currentTemperature").value(23))
                .andExpect(jsonPath("$.batchStock.[0].minimumTemperature").value(17))
                .andExpect(jsonPath("$.batchStock.[0].initialQuantity").value(5))
                .andExpect(jsonPath("$.batchStock.[0].currentQuantity").value(5))
                .andExpect(jsonPath("$.batchStock.[0].manufacturingDate").value("2021-05-01"))
                .andExpect(jsonPath("$.batchStock.[0].manufacturingTime").value("2018-02-27 18:14:01"))
                .andExpect(jsonPath("$.batchStock.[0].dueDate").value("2021-07-01"));
    }

    @Test
    void testInsertBatch_whenReceiveAInboundOrderTransactionWithoutOrderNumber_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().setOrderNumber("");

        this.mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Order Number is required"));
    }

    @Test
    void testInsertBatch_whenReceiveAInboundOrderTransactionWithoutOrderDate_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().setOrderDate("");

        this.mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Order Date  is required"));
    }

    @Test
    void testInsertBatch_whenReceiveAInboundOrderTransactionWithoutWarehouseCode_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getSection().setWarehouseCode("");

        this.mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Warehouse Code cant be empty"));
    }

    @Test
    void testInsertBatch_whenReceiveAInboundOrderTransactionWithoutBatchNumber_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setBatchNumber("");

        this.mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Batch Number is required"));
    }

    @Test
    void testInsertBatch_whenReceiveAInboundOrderTransactionWithoutProductId_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setProductId("");

        this.mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Product Id cant be empty"));
    }

    @Test
    void testInsertBatch_whenReceiveAInboundOrderTransactionWithoutCurrentTemperature_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setCurrentTemperature(null);

        this.mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Current Temperature is required"));
    }

    @Test
    void testInsertBatch_whenReceiveAInboundOrderTransactionWithoutMinimumTemperature_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setMinimumTemperature(null);

        this.mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Minimum Temperature is required"));
    }

    @Test
    void testInsertBatch_whenReceiveAInboundOrderTransactionWithoutManufacturingDate_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setManufacturingDate("");

        this.mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Manufacturing Date is required"));
    }

    @Test
    void testInsertBatch_whenReceiveAInboundOrderTransactionWithoutManufacturingTime_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setManufacturingTime("");

        this.mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Manufacturing Time is required"));
    }

    @Test
    void testInsertBatch_whenReceiveAInboundOrderTransactionWithoutDueDate_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setDueDate("");

        this.mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Due Date is required"));
    }

    @Test
    void testGetExistingInboundOrderId() throws Exception {

        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user","password", AuthorityUtils.createAuthorityList("REPRESENTATIVE"));
        when(batchService.saveBatch(any())).thenThrow(new ExistingInboundOrderId());
        when(batchService.validate(any())).thenReturn(1);
        this.mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer token " + jwtTokenUtil.generateToken(userDetails))
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The given InboundOrder Id already exist.Create a new one!"))
                .andExpect(jsonPath("$.code").value(400));
    }


    //------------------------------------------TESTS PUT METHOD PUTBACH--------------------------------------------------

    @Test
    void testPutBatch_whenReceiveAInboundOrderTransactionOK_thenReturnOkWithBatchStock() throws Exception {
        BatchStock batchStockResponse = createBatchStock();
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user","password", AuthorityUtils.createAuthorityList("REPRESENTATIVE"));
        when(batchService.putBatch(any())).thenReturn(batchStockResponse);
        when(batchService.validate(any())).thenReturn(1);

        this.mockMvc.perform(
                put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer token " + jwtTokenUtil.generateToken(userDetails))
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.batchStock.[0].batchNumber").value("1"))
                .andExpect(jsonPath("$.batchStock.[0].productId").value("1"))
                .andExpect(jsonPath("$.batchStock.[0].currentTemperature").value(23))
                .andExpect(jsonPath("$.batchStock.[0].minimumTemperature").value(17))
                .andExpect(jsonPath("$.batchStock.[0].initialQuantity").value(5))
                .andExpect(jsonPath("$.batchStock.[0].currentQuantity").value(5))
                .andExpect(jsonPath("$.batchStock.[0].manufacturingDate").value("2021-05-01"))
                .andExpect(jsonPath("$.batchStock.[0].manufacturingTime").value("2018-02-27 18:14:01"))
                .andExpect(jsonPath("$.batchStock.[0].dueDate").value("2021-07-01"));
    }

    @Test
    void testPutBatch_whenReceiveAInboundOrderTransactionWithoutOrderNumber_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().setOrderNumber("");

        this.mockMvc.perform(
                put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Order Number is required"));
    }

    @Test
    void testPutBatch_whenReceiveAInboundOrderTransactionWithoutOrderDate_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().setOrderDate("");

        this.mockMvc.perform(
                put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Order Date  is required"));
    }

    @Test
    void testPutBatch_whenReceiveAInboundOrderTransactionWithoutWarehouseCode_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getSection().setWarehouseCode("");

        this.mockMvc.perform(
                put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Warehouse Code cant be empty"));
    }

    @Test
    void testPutBatch_whenReceiveAInboundOrderTransactionWithoutBatchNumber_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setBatchNumber("");

        this.mockMvc.perform(
                put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Batch Number is required"));
    }

    @Test
    void testPutBatch_whenReceiveAInboundOrderTransactionWithoutProductId_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setProductId("");

        this.mockMvc.perform(
                put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Product Id cant be empty"));
    }

    @Test
    void testPutBatch_whenReceiveAInboundOrderTransactionWithoutCurrentTemperature_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setCurrentTemperature(null);

        this.mockMvc.perform(
                put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Current Temperature is required"));
    }

    @Test
    void testPutBatch_whenReceiveAInboundOrderTransactionWithoutMinimumTemperature_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setMinimumTemperature(null);

        this.mockMvc.perform(
                put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Minimum Temperature is required"));
    }

    @Test
    void testPutBatch_whenReceiveAInboundOrderTransactionWithoutManufacturingDate_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setManufacturingDate("");

        this.mockMvc.perform(
                put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Manufacturing Date is required"));
    }

    @Test
    void testPutBatch_whenReceiveAInboundOrderTransactionWithoutManufacturingTime_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setManufacturingTime("");

        this.mockMvc.perform(
                put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Manufacturing Time is required"));
    }

    @Test
    void testPutBatch_whenReceiveAInboundOrderTransactionWithoutDueDate_thenReturnBadRequest() throws Exception {
        inboundOrderTransactionRequest.getInboundOrder().getBatchStock().get(0).setDueDate("");

        this.mockMvc.perform(
                put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(inboundOrderTransactionRequest))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].message").value("Due Date is required"));
    }


    private InboundOrderTransactionRequest createInboundOrderTransactionRequest() {
        return new InboundOrderTransactionRequest(createInboundOrderDTORequest());
    }

    private BatchStock createBatchStock() {
        BatchStock batchStockResponse = new BatchStock();
        List<BatchResponse> batchResponses = new ArrayList<>();
        batchResponses.add(createBatchResponse());
        batchStockResponse.setBatchStock(batchResponses);
        return batchStockResponse;
    }


    private BatchResponse createBatchResponse() {
        BatchResponse batchResponse = new BatchResponse();
        batchResponse.setBatchNumber("1");
        batchResponse.setProductId("1");
        batchResponse.setCurrentTemperature(23F);
        batchResponse.setMinimumTemperature(17F);
        batchResponse.setInitialQuantity(5);
        batchResponse.setCurrentQuantity(5);
        batchResponse.setManufacturingDate("2021-05-01");
        batchResponse.setManufacturingTime("2018-02-27 18:14:01");
        batchResponse.setDueDate("2021-07-01");

        return batchResponse;
    }

    private InboundOrderDTORequest createInboundOrderDTORequest() {
        InboundOrderDTORequest inboundOrderDTORequest = new InboundOrderDTORequest();
        inboundOrderDTORequest.setOrderDate("2018-02-27");
        inboundOrderDTORequest.setOrderNumber("1");
        inboundOrderDTORequest.setSection(new SectionDTO(State.FF, "1"));
        List<BatchResponse> batchStock = new ArrayList<>();
        batchStock.add(createBatchResponse());
        inboundOrderDTORequest.setBatchStock(batchStock);
        return inboundOrderDTORequest;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class InboundOrderDTORequest {
        private String orderNumber;
        private String orderDate;
        private SectionDTO section;
        private List<BatchResponse> batchStock;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class InboundOrderTransactionRequest {
        private InboundOrderDTORequest inboundOrder;
    }


}