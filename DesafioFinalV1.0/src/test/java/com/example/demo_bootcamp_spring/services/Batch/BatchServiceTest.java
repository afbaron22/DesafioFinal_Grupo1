package com.example.demo_bootcamp_spring.services.Batch;
import com.example.demo_bootcamp_spring.dtos.BatchStockProduct;
import com.example.demo_bootcamp_spring.dtos.BatchStockProductSearch;
import com.example.demo_bootcamp_spring.dtos.SearchedWarehouseProducts;
import com.example.demo_bootcamp_spring.dtos.SectionDTO;
import com.example.demo_bootcamp_spring.models.*;
import com.example.demo_bootcamp_spring.repository.BatchRepository;
import com.example.demo_bootcamp_spring.repository.SectionRepository;
import net.bytebuddy.dynamic.DynamicType;
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
    @InjectMocks
    BatchService batchService;


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
        return new Product("productTest","productName","test",State.FS,120.0);
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


}