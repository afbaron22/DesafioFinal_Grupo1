package com.example.demo_bootcamp_spring.services.Batch;

import com.example.demo_bootcamp_spring.dtos.BatchStockProduct;
import com.example.demo_bootcamp_spring.dtos.BatchStockProductSearch;
import com.example.demo_bootcamp_spring.dtos.SectionDTO;
import com.example.demo_bootcamp_spring.exceptions.*;
import com.example.demo_bootcamp_spring.dtos.*;
import com.example.demo_bootcamp_spring.models.*;
import com.example.demo_bootcamp_spring.repository.BatchRepository;
import com.example.demo_bootcamp_spring.repository.InboundOrderRepository;
import com.example.demo_bootcamp_spring.repository.ProductsRepository;
import com.example.demo_bootcamp_spring.repository.SectionRepository;
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
    InboundOrderRepository inboundOrderRepository;
    @InjectMocks
    BatchService batchService;
    @InjectMocks
    ProductService productService;
    @Mock
    private ProductsRepository productsRepository;


    private InboundOrderTransaction inboundOrderTransaction;

    @BeforeEach
    public void setUp() {
        inboundOrderTransaction = createInboundOrderTransaction();
    }



    @Test
    public void shouldGetProductsFromBatches(){
        BatchStockProductSearch expected = createBatchStockProductSearch();
        List<Batch> batchList = createBatchListFS();
        when(batchRepository.findByProductId("productTest")).thenReturn(Optional.of(batchList));
        when(sectionRepository.findById("sectionTest")).thenReturn(Optional.of(createSectionFS()));
        assertEquals(expected,batchService.getProductFromBatches("productTest","Default"));
    }

    @Test
    public void shouldGetProductsFromBatchesOrderByCurrentQuantity(){
        BatchStockProductSearch expected = createBatchStockProductSearchOrderByCurrentQuantity();
        List<Batch> batchList = createBatchListFS();
        when(batchRepository.findByProductId("productTest")).thenReturn(Optional.of(batchList));
        when(sectionRepository.findById("sectionTest")).thenReturn(Optional.of(createSectionFS()));
        assertEquals(expected,batchService.getProductFromBatches("productTest","C"));
    }

    @Test
    public void shouldGetProductsFromBatchesOrderByDueDate(){
        BatchStockProductSearch expected = createBatchStockProductSearchOrderByDueDate();
        List<Batch> batchList = createBatchListFS();
        when(batchRepository.findByProductId("productTest")).thenReturn(Optional.of(batchList));
        when(sectionRepository.findById("sectionTest")).thenReturn(Optional.of(createSectionFS()));
        assertEquals(expected,batchService.getProductFromBatches("productTest","F"));
    }

    @Test
    public void shouldGetProductsFromBatchesOrderByBatchNumber(){
        BatchStockProductSearch expected = createBatchStockProductSearchOrderByBatchNumber();
        List<Batch> batchList = createBatchListFS();
        when(batchRepository.findByProductId("productTest")).thenReturn(Optional.of(batchList));
        when(sectionRepository.findById("sectionTest")).thenReturn(Optional.of(createSectionFS()));
        assertEquals(expected,batchService.getProductFromBatches("productTest","L"));
    }

    @Test
    public void shouldThrownInvalidSectionId(){
        List<Batch> batchList = createBatchListFS();
        when(batchRepository.findByProductId("productTest")).thenReturn(Optional.of(batchList));
        when(sectionRepository.findById("badSectionTest")).thenThrow(InvalidSectionId.class);
        assertThrows(InvalidSectionId.class, () -> {
            batchService.getProductFromBatches("productTest","L");
        });
    }

    @Test
    void shouldGetInvalidSectionId() {
        Optional<Section> optionalSection = Optional.empty();
        List<Batch> batchList = createBatchListFS();
        when(batchRepository.findByProductId("productTest")).thenReturn(Optional.of(batchList));
        when(sectionRepository.findById("sectionTest")).thenReturn(Optional.of(createSectionFS()));
        when(sectionRepository.findById(any())).thenReturn(optionalSection);

        assertThrows(InvalidSectionId.class, () -> {
            batchService.getProductFromBatches("productTest","Default");
        });
    }

    @Test
    public void shouldGetProductFromWarehouse(){

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

    @Test
    public void shouldGetBatchesInWarehouseByDueDateFS(){
        BatchStockWareHouse listBatchExpected = createBatchStockWareHouse();
        List<Batch> listBatch = createBatchListFS();
        when(batchRepository.findProductDueDate("60")).thenReturn(Optional.of(listBatch));
        assertEquals(listBatchExpected,batchService.getBatchesInWarehouseByDueDate(60,27,"FS","asc"));
    }

    @Test
    public void shouldGetBatchesInWarehouseByDueDateFF(){
        BatchStockWareHouse listBatchExpected = createBatchStockWareHouse();
        List<Batch> listBatch = createBatchListFF();
        when(batchRepository.findProductDueDate("60")).thenReturn(Optional.of(listBatch));
        assertEquals(listBatchExpected,batchService.getBatchesInWarehouseByDueDate(60,27,"FF","asc"));
    }

    @Test
    public void shouldGetBatchesInWarehouseByDueDateRF(){
        BatchStockWareHouse listBatchExpected = createBatchStockWareHouse();
        List<Batch> listBatch = createBatchListRF();
        when(batchRepository.findProductDueDate("60")).thenReturn(Optional.of(listBatch));
        assertEquals(listBatchExpected,batchService.getBatchesInWarehouseByDueDate(60,27,"RF","asc"));
    }

    private BatchStockWareHouse createBatchStockWareHouse(){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> mapBatch = new HashMap<>();
        mapBatch.put("batchNumber","1");
        mapBatch.put("productId","1");
        mapBatch.put("dueDate",LocalDate.of(2021,7,12));
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
        return new Product("1","productName","test",State.FS, 4000.0);
    }
    private Product createProductFF(){
        return new Product("1","productName","test",State.FF, 4000.0);
    }
    private Product createProductRF(){
        return new Product("1","productName","test",State.RF, 4000.0);
    }


    private InboundOrder createInboundOrderFS(){
        LocalDate date = LocalDate.of(2021,5,14);
        return new InboundOrder("numberTest",date, createSectionFS());
    }
    private InboundOrder createInboundOrderFF(){
        LocalDate date = LocalDate.of(2021,5,14);
        return new InboundOrder("numberTest",date, createSectionFF());
    }
    private InboundOrder createInboundOrderRF(){
        LocalDate date = LocalDate.of(2021,5,14);
        return new InboundOrder("numberTest",date, createSectionRF());
    }

    private Section createSectionFS(){
        return new Section("sectionTest",State.FS,"testWHC",20,20,500);
    }
    private Section createSectionFF(){
        return new Section("sectionTest",State.FF,"testWHC",20,20,500);
    }
    private Section createSectionRF(){
        return new Section("sectionTest",State.RF,"testWHC",20,20,500);
    }

    private List<Batch> createBatchListFS(){
        Product product = createProductFS();
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2021,5,14),LocalTime.of(1,1,1));
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

    private List<Batch> createBatchListFF(){
        Product product = createProductFF();
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2021,5,14),LocalTime.of(1,1,1));
        LocalDate expirationDate = LocalDate.of(2021,7,14);
        LocalDate expirationDate1 = LocalDate.of(2021,7,13);
        LocalDate expirationDate2 = LocalDate.of(2021,7,12);
        LocalDate date = LocalDate.of(2021,5,14);
        LocalDate date1 = LocalDate.of(2021,5,14);
        LocalDate date2 = LocalDate.of(2021,5,14);
        List<Batch> batchList = new ArrayList<>();
        batchList.add(new Batch("0",product,(float)20,(float)20,expirationDate,date,dateTime,500,500, createInboundOrderFF()));
        batchList.add(new Batch("1",product,(float)20,(float)20,expirationDate2,date1,dateTime,500,300, createInboundOrderFF()));
        batchList.add(new Batch("2",product,(float)20,(float)20,expirationDate1,date2,dateTime,500,400, createInboundOrderFF()));
        return batchList;
    }

    private List<Batch> createBatchListRF(){
        Product product = createProductRF();
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2021,5,14),LocalTime.of(1,1,1));
        LocalDate expirationDate = LocalDate.of(2021,7,14);
        LocalDate expirationDate1 = LocalDate.of(2021,7,13);
        LocalDate expirationDate2 = LocalDate.of(2021,7,12);
        LocalDate date = LocalDate.of(2021,5,14);
        LocalDate date1 = LocalDate.of(2021,5,14);
        LocalDate date2 = LocalDate.of(2021,5,14);
        List<Batch> batchList = new ArrayList<>();
        batchList.add(new Batch("0",product,(float)20,(float)20,expirationDate,date,dateTime,500,500, createInboundOrderRF()));
        batchList.add(new Batch("1",product,(float)20,(float)20,expirationDate2,date1,dateTime,500,300, createInboundOrderRF()));
        batchList.add(new Batch("2",product,(float)20,(float)20,expirationDate1,date2,dateTime,500,400, createInboundOrderRF()));
        return batchList;
    }

    private List<BatchStockProduct> createBatchStockProductOrderByCurrentQuantity(){
        List<BatchStockProduct> listFound = new ArrayList<>();
        LocalDate date = LocalDate.of(2021,7,14);
        LocalDate date1 = LocalDate.of(2021,7,13);
        LocalDate date2 = LocalDate.of(2021,7,12);
        listFound.add(new BatchStockProduct("1",300,date2));
        listFound.add(new BatchStockProduct("2",400,date1));
        listFound.add(new BatchStockProduct("0",500,date));
        return listFound;
    }

    private List<BatchStockProduct> createBatchStockProductOrderByBatchNumber(){
        List<BatchStockProduct> listFound = new ArrayList<>();
        LocalDate date = LocalDate.of(2021,7,14);
        LocalDate date1 = LocalDate.of(2021,7,13);
        LocalDate date2 = LocalDate.of(2021,7,12);
        listFound.add(new BatchStockProduct("0",500,date));
        listFound.add(new BatchStockProduct("1",300,date2));
        listFound.add(new BatchStockProduct("2",400,date1));
        return listFound;
    }

    private List<BatchStockProduct> createBatchStockProductOrderByDueDate(){
        List<BatchStockProduct> listFound = new ArrayList<>();
        LocalDate date = LocalDate.of(2021,7,14);
        LocalDate date1 = LocalDate.of(2021,7,13);
        LocalDate date2 = LocalDate.of(2021,7,12);
        listFound.add(new BatchStockProduct("1",300,date2));
        listFound.add(new BatchStockProduct("2",400,date1));
        listFound.add(new BatchStockProduct("0",500,date));
        return listFound;
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

    private BatchStockProductSearch createBatchStockProductSearchOrderByDueDate(){
        SectionDTO sectionDto = createSectionDto();
        List<BatchStockProduct> listFound = createBatchStockProductOrderByDueDate();
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


    private BatchStockProductSearch createBatchStockProductSearchOrderByBatchNumber(){
        SectionDTO sectionDto = createSectionDto();
        List<BatchStockProduct> listFound = createBatchStockProductOrderByBatchNumber();
        return new BatchStockProductSearch(sectionDto,"productTest",listFound);
    }
}