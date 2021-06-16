package com.example.demo_bootcamp_spring.services.Batch;
import com.example.demo_bootcamp_spring.dtos.BatchStockProduct;
import com.example.demo_bootcamp_spring.dtos.BatchStockProductSearch;
import com.example.demo_bootcamp_spring.dtos.SectionDTO;
import com.example.demo_bootcamp_spring.exceptions.InvalidSectionId;
import com.example.demo_bootcamp_spring.models.*;
import com.example.demo_bootcamp_spring.repository.BatchRepository;
import com.example.demo_bootcamp_spring.repository.SectionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void shouldGetProductsFromBatchesOrderByDueDate(){
        BatchStockProductSearch expected = createBatchStockProductSearchOrderByDueDate();
        List<Batch> batchList = createBatchList();
        when(batchRepository.findByProductId("productTest")).thenReturn(Optional.of(batchList));
        when(sectionRepository.findById("sectionTest")).thenReturn(Optional.of(createSection()));
        assertEquals(expected,batchService.getProductFromBatches("productTest","F"));
    }

    @Test
    public void shouldGetProductsFromBatchesOrderByBatchNumber(){
        BatchStockProductSearch expected = createBatchStockProductSearchOrderByBatchNumber();
        List<Batch> batchList = createBatchList();
        when(batchRepository.findByProductId("productTest")).thenReturn(Optional.of(batchList));
        when(sectionRepository.findById("sectionTest")).thenReturn(Optional.of(createSection()));
        assertEquals(expected,batchService.getProductFromBatches("productTest","L"));
    }

    @Test
    public void shouldThrownInvalidSectionId(){
        List<Batch> batchList = createBatchList();
        when(batchRepository.findByProductId("productTest")).thenReturn(Optional.of(batchList));
        when(sectionRepository.findById("badSectionTest")).thenThrow(InvalidSectionId.class);
        assertThrows(InvalidSectionId.class, () -> {
            batchService.getProductFromBatches("productTest","L");
        });
    }

    private Product createProduct(){
        return new Product("productTest","productName","test",State.FS);
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
        LocalDate expirationDate1 = LocalDate.of(2021,7,13);
        LocalDate expirationDate2 = LocalDate.of(2021,7,12);
        LocalDate date = LocalDate.of(2021,5,14);
        LocalDate date1 = LocalDate.of(2021,5,14);
        LocalDate date2 = LocalDate.of(2021,5,14);
        List<Batch> batchList = new ArrayList<>();
        batchList.add(new Batch("0",product,(float)20,(float)20,expirationDate,date,dateTime,500,500,createInboundOrder()));
        batchList.add(new Batch("1",product,(float)20,(float)20,expirationDate2,date1,dateTime,500,300,createInboundOrder()));
        batchList.add(new Batch("2",product,(float)20,(float)20,expirationDate1,date2,dateTime,500,400,createInboundOrder()));
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

    private BatchStockProductSearch createBatchStockProductSearchOrderByBatchNumber(){
        SectionDTO sectionDto = createSectionDto();
        List<BatchStockProduct> listFound = createBatchStockProductOrderByBatchNumber();
        return new BatchStockProductSearch(sectionDto,"productTest",listFound);
    }
}