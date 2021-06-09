package com.mercadolibre.demo_bootcamp_spring.services;


import com.mercadolibre.demo_bootcamp_spring.dtos.BatchDTO;
import com.mercadolibre.demo_bootcamp_spring.dtos.InboundOrderDTO;
import com.mercadolibre.demo_bootcamp_spring.models.Batch;
import com.mercadolibre.demo_bootcamp_spring.models.InboundOrder;
import com.mercadolibre.demo_bootcamp_spring.models.Section;
import com.mercadolibre.demo_bootcamp_spring.repository.BatchRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.InboundOrderRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.ProductsRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.SectionRepository;
import com.mercadolibre.demo_bootcamp_spring.services.Batch.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchService implements IBatchService {

    @Autowired
    InboundOrderRepository inboundOrderRepository;
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    ProductsRepository productsRepository;

    public List<Batch> saveBatch(InboundOrderDTO inboundOrderDTO){

        var sizeBatch = getBatchSize(inboundOrderDTO);
        var section = saveSection(inboundOrderDTO,sizeBatch);
        var newInboundOrder = new InboundOrder(inboundOrderDTO.getOrderNumber(),inboundOrderDTO.getOrderDate(),section);
        var batches = inboundOrderDTO.getBatchStock().stream().map(x-> returnBatch(x,newInboundOrder)).collect(Collectors.toList());
        inboundOrderRepository.save(newInboundOrder);
        batches.forEach(x->batchRepository.save(x));
        return batches;

    }

    private Integer getBatchSize(InboundOrderDTO inboundOrderDTO){
        return  inboundOrderDTO
                .getBatchStock().stream()
                .map(x-> {
                    return x.getCurrentQuantity();
                }).collect(Collectors.toList()).stream().reduce(0,(a,b)->a+b);
    }

    private Section saveSection(InboundOrderDTO inboundOrderDTO,Integer sizeBatch){
        var section = new Section(null,inboundOrderDTO.getSection().getSectionCode(),"1",0,0,sizeBatch);
        sectionRepository.save(section);
        return section;
    }

    private Batch returnBatch(BatchDTO batchDTO,InboundOrder inboundOrder){
        var productTest =productsRepository.findById(batchDTO.getProductId()).orElseThrow();
        var newBatch = new Batch(null,productTest,batchDTO.getCurrentTemperature(),batchDTO.getMinimumTemperature(),batchDTO.getDueDate(),batchDTO.getManufacturingDate(),batchDTO.getManufacturingTime(),batchDTO.getInitialQuantity(),batchDTO.getCurrentQuantity(),inboundOrder);
        return newBatch;
    }

}
