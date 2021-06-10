package com.mercadolibre.demo_bootcamp_spring.unit;

import com.mercadolibre.demo_bootcamp_spring.dtos.BatchDTO;
import com.mercadolibre.demo_bootcamp_spring.models.Batch;
import com.mercadolibre.demo_bootcamp_spring.models.InboundOrder;
import com.mercadolibre.demo_bootcamp_spring.models.Product;
import com.mercadolibre.demo_bootcamp_spring.repository.ProductsRepository;
import com.mercadolibre.demo_bootcamp_spring.services.Batch.BatchService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class BatchServiceTest {

    @Mock
    ProductsRepository productsRepository;

    @InjectMocks
    BatchService service;

  /*  @Test
    void testReturnBatch() {
        Product product = new Product()
        when(productsRepository.findById("1")).thenReturn(java.util.Optional.of(Datos.CUENTA_001));

    }
    private Batch returnBatch(BatchDTO batchDTO, InboundOrder inboundOrder){
        var productTest =productsRepository.findById(batchDTO.getProductId()).orElseThrow();
        var newBatch = new Batch(null,productTest,batchDTO.getCurrentTemperature(),batchDTO.getMinimumTemperature(),batchDTO.getDueDate(),batchDTO.getManufacturingDate(),batchDTO.getManufacturingTime(),batchDTO.getInitialQuantity(),batchDTO.getCurrentQuantity(),inboundOrder);
        return newBatch;
    }*/
}
