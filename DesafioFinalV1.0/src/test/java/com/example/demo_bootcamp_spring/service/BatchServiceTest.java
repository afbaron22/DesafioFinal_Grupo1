package com.example.demo_bootcamp_spring.service;
import com.example.demo_bootcamp_spring.dtos.*;
import com.example.demo_bootcamp_spring.exceptions.NoRelatedWarehousesToProduct;
import com.example.demo_bootcamp_spring.exceptions.NonExistentProductException;
import com.example.demo_bootcamp_spring.exceptions.NotExistingBatch;
import com.example.demo_bootcamp_spring.exceptions.NotFoundInboundOrderId;
import com.example.demo_bootcamp_spring.models.*;
import com.example.demo_bootcamp_spring.repository.BatchRepository;
import com.example.demo_bootcamp_spring.repository.InboundOrderRepository;
import com.example.demo_bootcamp_spring.repository.ProductsRepository;
import com.example.demo_bootcamp_spring.repository.SectionRepository;
import com.example.demo_bootcamp_spring.services.Batch.BatchService;
import com.example.demo_bootcamp_spring.services.Product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BatchServiceTest {

    @Mock
    BatchRepository batchRepository;
    @Mock
    SectionRepository sectionRepository;
    @Mock
    private InboundOrderRepository inboundOrderRepository;

    @InjectMocks
    BatchService batchService;
    @InjectMocks
    ProductService productService;

    @Mock
    private
    ProductsRepository productsRepository;

    private InboundOrderTransaction inboundOrderTransaction;

    @BeforeEach
    public void setUp() {
        inboundOrderTransaction = createInboundOrderTransaction();
    }


    @Test
    public void shouldGetProductsFromBatches(){
        BatchStockProductSearch expected = createBatchStockProductSearch();
        List<Batch> batchList = createBatchList();
        when(batchRepository.findByProductId("productTest")).thenReturn(Optional.of(batchList));
        when(sectionRepository.findById("sectionTest")).thenReturn(Optional.of(createSection()));
        assertEquals(expected,batchService.getProductFromBatches("productTest","Default"));
    }

    @Test
    public void shouldGetProductsFromBatchesOrderByCurrentQuantity(){
        BatchStockProductSearch expected = createBatchStockProductSearchOrderByCurrentQuantity();
        List<Batch> batchList = createBatchList();
        when(batchRepository.findByProductId("productTest")).thenReturn(Optional.of(batchList));
        when(sectionRepository.findById("sectionTest")).thenReturn(Optional.of(createSection()));
        assertEquals(expected,batchService.getProductFromBatches("productTest","C"));
    }

    @Test
    public void shouldGetProductsFromBatchesOrderByExpirationDate(){

    }

    @Test
    public void shoudGetProductFromWarehouse(){

        List<Map<String,String>> expectedWarehouses = new ArrayList<>();
        Map<String,String> expectedWarehouse = new HashMap<>();
        expectedWarehouse.put("totalQuantity","123");
        expectedWarehouse.put("warehousecode","1");
        expectedWarehouses.add(expectedWarehouse);
        SearchedWarehouseProducts expectedWarehouseProducts = new SearchedWarehouseProducts();
        expectedWarehouseProducts.setWarehouses(expectedWarehouses);
        expectedWarehouseProducts.setProductId("1");

        List<Object[]> warehouses = new ArrayList<>();
        Object[] warehouseQuantity = {"1","123"};
        warehouses.add(warehouseQuantity);
        when(batchRepository.findWarehousesWithProduct("1")).thenReturn(Optional.of(warehouses));
        assertEquals(expectedWarehouseProducts,batchService.getProductFromWarehouses("1"));
    }


    private Product createProduct(){
        return new Product("productTest","productName","test",State.FS, 4000.0);
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
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2021,5,14),LocalTime.of(1,1,1));
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

    private List<BatchStockProduct> createBatchStockProductWithNullValues(){
        List<BatchStockProduct> listFound = new ArrayList<>();
        LocalDate date = LocalDate.of(2021,7,14);
        LocalDate date1 = LocalDate.of(2021,7,14);
        LocalDate date2 = LocalDate.of(2021,7,14);
        listFound.add(new BatchStockProduct("test",500,date));
        listFound.add(new BatchStockProduct("test1",500,date1));
        listFound.add(null);
        listFound.add(new BatchStockProduct("test2",500,date2));
        listFound.add(null);
        return listFound;
    }

    private List<BatchStockProduct> createBatchStockProductOrderByCurrentQuantity(){
        List<BatchStockProduct> listFound = new ArrayList<>();
        LocalDate date = LocalDate.of(2021,7,14);
        LocalDate date1 = LocalDate.of(2021,7,14);
        LocalDate date2 = LocalDate.of(2021,7,14);
        listFound.add(new BatchStockProduct("test1",300,date));
        listFound.add(new BatchStockProduct("test2",400,date1));
        listFound.add(new BatchStockProduct("test",500,date2));
        return listFound;
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

    private BatchStockProductSearch createBatchStockProductSearchOrderByCurrentQuantity(){
        SectionDTO sectionDto = createSectionDto();
        List<BatchStockProduct> listFound = createBatchStockProductOrderByCurrentQuantity();
        return new BatchStockProductSearch(sectionDto,"productTest",listFound);
    }

    //------------------------------------------TESTS METHOD SAVEBATCH--------------------------------------------------

    @Test
    void whenReceiveOkInboundOrderTransaction_thenSaveBatch() {
        when(productsRepository.findById(inboundOrderTransaction.getInboundOrder().getBatchStock().get(0).getProductId())).thenReturn(createProduct2());
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


    //TODO hacer saltar excepcion GetExistingInboundOrderId
//    @Test
//    void shouldGetExistingInboundOrderId() {
//
//        when(inboundOrderRepository.findById(inboundOrderTransaction.getInboundOrder().getOrderNumber()).isPresent()).thenReturn(true);
//
//        assertThrows(ExistingInboundOrderId.class, () -> {
//            batchService.saveBatch(inboundOrderTransaction);
//        });
//    }


    @Test
    void shouldGetNonExistentProductException() {
        Optional<Product> optionalProduct = Optional.empty();

        when(productsRepository.findById(inboundOrderTransaction.getInboundOrder().getBatchStock().get(0).getProductId())).thenReturn(optionalProduct);

        assertThrows(NonExistentProductException.class, () -> {
            batchService.saveBatch(inboundOrderTransaction);
        });
    }

    //------------------------------------------TESTS METHOD PUTBATCH--------------------------------------------------
    @Test

    void whenReceiveOkInboundOrderTransaction_thenPutBatch() {
        when(inboundOrderRepository.findById(inboundOrderTransaction.getInboundOrder().getOrderNumber())).thenReturn(createInboundOrder2());
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

    @Test
    void shouldGetNotFoundInboundOrderId() {
        Optional<InboundOrder> optionalInboundOrder = Optional.empty();
        when(inboundOrderRepository.findById(inboundOrderTransaction.getInboundOrder().getBatchStock().get(0).getProductId())).thenReturn(optionalInboundOrder);

        assertThrows(NotFoundInboundOrderId.class, () -> {
            batchService.putBatch(inboundOrderTransaction);
        });
    }

    @Test
    void shouldGetNotExistingBatch() {
        Optional<List<Batch>> optionalBatches = Optional.empty();
        when(inboundOrderRepository.findById(inboundOrderTransaction.getInboundOrder().getOrderNumber())).thenReturn(createInboundOrder2());
        when(batchRepository.findByInboundOrder(inboundOrderTransaction.getInboundOrder().getOrderNumber())).thenReturn(optionalBatches);


        assertThrows(NotExistingBatch.class, () -> {
            batchService.putBatch(inboundOrderTransaction);
        });
    }
    //------------------------------------------TESTS METHOD GETPRODUCTSBYCATEGORY--------------------------------------------------


    @Test
    void testGetProductsByCategory() {
        when(batchRepository.findProduct(State.FF)).thenReturn(java.util.Optional.of(createProductList()));
        List<Product> productList =productService.getProductsByCategory(State.FF);
        assertEquals("1", productList.get(0).getProductId());
        assertEquals("2", productList.get(1).getProductId());
        assertEquals("product1", productList.get(0).getName());
        assertEquals("product2", productList.get(1).getName());
        assertEquals(State.FF, productList.get(0).getState());
        assertEquals(State.FF, productList.get(1).getState());
    }
    @Test
    void testGetProductsByCategoryException() {

        when(batchRepository.findProduct(State.FF)).thenThrow(new NoRelatedWarehousesToProduct());

        assertThrows(NoRelatedWarehousesToProduct.class, () -> {
            productService.getProductsByCategory(State.FF);
        });
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

    public Optional<Product> createProduct2() {
        Product product = new Product();
        product.setName("product1");
        product.setProductId("1");
        product.setAdditionalInfo("infoAdd");
        product.setState(State.FF);
        Optional<Product> optionalProduct = Optional.of(product);
        return optionalProduct;
    }

    public Optional<InboundOrder> createInboundOrder2() {

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
        batch.setProduct(createProduct2().get());
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
    public List<Product> createProductList(){
        Product product = new Product();
        product.setName("product1");
        product.setProductId("1");
        product.setAdditionalInfo("infoAdd");
        product.setState(State.FF);
        Product product2 = new Product();
        product2.setName("product2");
        product2.setProductId("2");
        product2.setAdditionalInfo("infoAdd");
        product2.setState(State.FF);
        return List.of(product,product2);
    }

}