package com.example.demo2.Unit.BatchService;


import com.example.demo2.dtos.*;
import com.example.demo2.models.*;
import com.example.demo2.repository.BatchRepository;
import com.example.demo2.repository.InboundOrderRepository;
import com.example.demo2.repository.ProductsRepository;
import com.example.demo2.repository.SectionRepository;
import com.example.demo2.services.Batch.BatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BatchServiceTest {

    @Mock
    private InboundOrderRepository inboundOrderRepository;
    @Mock
    private
    SectionRepository sectionRepository;
    @Mock
    private
    BatchRepository batchRepository;
    @Mock
    private
    ProductsRepository productsRepository;

    @InjectMocks
    BatchService batchService;

    private InboundOrderTransaction inboundOrderTransaction;

    @BeforeEach
    public void setUp() {
        inboundOrderTransaction = createInboundOrderTransaction();
    }


    @Test
    void whenReceiveOkInboundOrderTransaction_thenSaveBatch() {
        when(productsRepository.findById(inboundOrderTransaction.getInboundOrder().getBatchStock().get(0).getProductId())).thenReturn(createProduct());
        BatchStock batchStock = batchService.saveBatch(inboundOrderTransaction);

        assertEquals("1", batchStock.getBatchStock().get(0).getBatchNumber());
        assertEquals("1", batchStock.getBatchStock().get(0).getProductId());
        assertEquals(5, batchStock.getBatchStock().get(0).getInitialQuantity());
        assertEquals(10, batchStock.getBatchStock().get(0).getCurrentQuantity());
        assertEquals("2021-05-01", batchStock.getBatchStock().get(0).getManufacturingDate());
        assertEquals("2021-06-05T10:00", batchStock.getBatchStock().get(0).getManufacturingTime());
        assertEquals("2021-07-01", batchStock.getBatchStock().get(0).getDueDate());
        assertEquals(5, batchStock.getBatchStock().get(0).getCurrentTemperature());
        assertEquals(10, batchStock.getBatchStock().get(0).getMinimumTemperature());


    }

    @Test
    void whenReceiveOkInboundOrderTransaction_thenPutBatch() {
        when(productsRepository.findById(inboundOrderTransaction.getInboundOrder().getBatchStock().get(0).getProductId())).thenReturn(createProduct());
        when(inboundOrderRepository.findById(inboundOrderTransaction.getInboundOrder().getOrderNumber())).thenReturn(createInboundOrder());
        when(batchRepository.findByInboundOrder(inboundOrderTransaction.getInboundOrder().getOrderNumber())).thenReturn(createListBatch());
        BatchStock batchStock = batchService.putBatch(inboundOrderTransaction);


        assertEquals("1", batchStock.getBatchStock().get(0).getBatchNumber());
        assertEquals("1", batchStock.getBatchStock().get(0).getProductId());
        assertEquals(5, batchStock.getBatchStock().get(0).getInitialQuantity());
        assertEquals(10, batchStock.getBatchStock().get(0).getCurrentQuantity());
        assertEquals("2021-05-01", batchStock.getBatchStock().get(0).getManufacturingDate());
        assertEquals("2021-06-05T10:00", batchStock.getBatchStock().get(0).getManufacturingTime());
        assertEquals("2021-07-01", batchStock.getBatchStock().get(0).getDueDate());
        assertEquals(5, batchStock.getBatchStock().get(0).getCurrentTemperature());
        assertEquals(10, batchStock.getBatchStock().get(0).getMinimumTemperature());


    }


    private InboundOrderDTO createInboundOrderDTO() {

        LocalDate date1 = LocalDate.of(2021, 6, 10);
        List<BatchDTO> batchStock = new ArrayList<>();
        batchStock.add(createBatchDTO());

        InboundOrderDTO inboundOrderDTO = new InboundOrderDTO();
        inboundOrderDTO.setOrderDate(date1);
        inboundOrderDTO.setOrderNumber("1");
        inboundOrderDTO.setSection(new SectionDTO(State.FF, "1"));
        inboundOrderDTO.setBatchStock(batchStock);

        return inboundOrderDTO;

    }

    private InboundOrderTransaction createInboundOrderTransaction() {
        return new InboundOrderTransaction(createInboundOrderDTO());
    }

    private BatchDTO createBatchDTO() {
        LocalDate manufacturingDate = LocalDate.of(2021, 5, 1);
        LocalDateTime manufacturingTime = LocalDateTime.of(2021, 6, 5, 10, 00);
        LocalDate dueDate = LocalDate.of(2021, 7, 1);

        BatchDTO batchDTO = new BatchDTO();
        batchDTO.setBatchNumber("1");
        batchDTO.setProductId("1");
        batchDTO.setCurrentTemperature(5F);
        batchDTO.setMinimumTemperature(10F);
        batchDTO.setInitialQuantity(5);
        batchDTO.setCurrentQuantity(10);
        batchDTO.setManufacturingDate(manufacturingDate);
        batchDTO.setManufacturingTime(manufacturingTime);
        batchDTO.setDueDate(dueDate);

        return batchDTO;
    }

    public Optional<Product> createProduct() {
        Product product = new Product();
        product.setName("product1");
        product.setProductId("1");
        product.setAdditionalInfo("infoAdd");
        product.setState(State.FF);
        Optional<Product> optionalProduct = Optional.of(product);
        return optionalProduct;
    }

    public Optional<InboundOrder> createInboundOrder() {

        LocalDate date1 = LocalDate.of(2021, 6, 10);
        List<BatchDTO> batchStock = new ArrayList<>();
        batchStock.add(createBatchDTO());

        InboundOrder inboundOrder = new InboundOrder();

        inboundOrder.setOrderDate(date1);
        inboundOrder.setOrderNumber("1");
        inboundOrder.setSection(new Section());

        Optional<InboundOrder> optionalInboundOrder = Optional.of(inboundOrder);
        return optionalInboundOrder;
    }

    public Optional<List<Batch>> createListBatch() {
        List<Batch> batches = new ArrayList<>();

        LocalDate manufacturingDate = LocalDate.of(2021, 5, 1);
        LocalDateTime manufacturingTime = LocalDateTime.of(2021, 6, 5, 10, 00);
        LocalDate dueDate = LocalDate.of(2021, 7, 1);

        Batch batch = new Batch();
        batch.setBatchNumber("1");
        batch.setProduct(createProduct().get());
        batch.setCurrentTemperature(5F);
        batch.setMinimumTemperature(10F);
        batch.setInitialQuantity(5);
        batch.setCurrentQuantity(10);
        batch.setManufacturingDate(manufacturingDate);
        batch.setManufacturingTime(manufacturingTime);
        batch.setDueDate(dueDate);
        batches.add(batch);

        Optional<List<Batch>> optionalBatches = Optional.of(batches);

        return optionalBatches;
    }


}
