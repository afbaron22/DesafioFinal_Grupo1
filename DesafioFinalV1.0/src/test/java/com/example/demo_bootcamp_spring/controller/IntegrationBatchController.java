package com.example.demo_bootcamp_spring.controller;



import com.example.demo_bootcamp_spring.dtos.BatchResponse;
import com.example.demo_bootcamp_spring.dtos.BatchStock;
import com.example.demo_bootcamp_spring.dtos.SectionDTO;
import com.example.demo_bootcamp_spring.models.State;
import com.example.demo_bootcamp_spring.services.Batch.BatchService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @MockBean
    private BatchService batchService;

    private InboundOrderTransactionRequest inboundOrderTransactionRequest;

    @BeforeEach
    public void setUp() {
        inboundOrderTransactionRequest = createInboundOrderTransactionRequest();
    }

    //------------------------------------------TESTS POST METHOD SAVEBATCH--------------------------------------------------
    @Test
    void testInsertBatch_whenReceiveAInboundOrderTransactionOK_thenReturnOkWithBatchStock() throws Exception {
        BatchStock batchStockResponse = createBatchStock();
        when(batchService.saveBatch(any())).thenReturn(batchStockResponse);

        this.mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
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


    //------------------------------------------TESTS PUT METHOD PUTBACH--------------------------------------------------

    @Test
    void testPutBatch_whenReceiveAInboundOrderTransactionOK_thenReturnOkWithBatchStock() throws Exception {
        BatchStock batchStockResponse = createBatchStock();
        when(batchService.putBatch(any())).thenReturn(batchStockResponse);

        this.mockMvc.perform(
                put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
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









/*
    @Test
    void detalle() throws Exception {
        when(cuentaService.findById(1L)).thenReturn(Datos.crearCuenta001());
        this.mvc.perform(get("/api/cuentas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.persona").value("Andres"))
                .andReturn();
        verify(cuentaService,atLeast(1)).findById(1L);
    }

    @Test
    void testTransferir() throws Exception {
        TransaccionDto dto = new TransaccionDto();
        dto.setCuentaDestinoId(2L);
        dto.setCuentaOrigenId(1l);
        dto.setMonto(new BigDecimal("100"));
        dto.setBancoId(1L);
        this.mvc.perform(post("/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.mensaje").value("Transferencia realizada con exito"));

    }
*/

}
