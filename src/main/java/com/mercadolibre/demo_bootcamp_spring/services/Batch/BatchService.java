package com.mercadolibre.demo_bootcamp_spring.services.Batch;


import com.mercadolibre.demo_bootcamp_spring.dtos.*;
import com.mercadolibre.demo_bootcamp_spring.exceptions.BatchNotFoundException;
import com.mercadolibre.demo_bootcamp_spring.exceptions.NonExistentProductException;
import com.mercadolibre.demo_bootcamp_spring.models.Batch;
import com.mercadolibre.demo_bootcamp_spring.models.InboundOrder;
import com.mercadolibre.demo_bootcamp_spring.models.Section;
import com.mercadolibre.demo_bootcamp_spring.repository.BatchRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.InboundOrderRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.ProductsRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.SectionRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchService implements IBatchService {

    InboundOrderRepository inboundOrderRepository;
    SectionRepository      sectionRepository;
    BatchRepository        batchRepository;
    ProductsRepository     productsRepository;

    /**
     * Contructor encargado de inyectar las dependencias de los repositorios.
     * @param inboundOrderRepository
     * @param sectionRepository
     * @param batchRepository
     * @param productsRepository
     */
    public BatchService(InboundOrderRepository inboundOrderRepository, SectionRepository sectionRepository, BatchRepository batchRepository, ProductsRepository productsRepository) {
        this.inboundOrderRepository = inboundOrderRepository;
        this.sectionRepository      = sectionRepository;
        this.batchRepository        = batchRepository;
        this.productsRepository     = productsRepository;
    }
    /**
     *Este método se encarga de guardar en persistencia un InboundOrderTransaction, este es un objecto que el
     * usuario envia, posteriormente el metodo extrae el contenido de este inbound y extraé los parametros
     * requeridos para guardar todos los datos en las tablas , estos atributos son el tamaño del bache, la sección
     * y finalmente se crea un nuevo inboundorder.Cabe aclarar que tambien se deben guardar los baches para que
     * la relación funcione y queden los datos almacenados.
     * @param inboundOrder
     * @return
     */
    //------------------------------------------MÉTODO SAVEBATCH--------------------------------------------------
    public BatchStock saveBatch(InboundOrderTransaction inboundOrder){
        var inboundOrderDTO = inboundOrder.getInboundOrder();
        var sizeBatch = getBatchSize(inboundOrderDTO);
        var section   = saveSection(inboundOrderDTO,sizeBatch);
        var newInboundOrder   = new InboundOrder(inboundOrderDTO.getOrderNumber(),inboundOrderDTO.getOrderDate(),section);
        var batches = inboundOrderDTO.getBatchStock().stream().map(x-> returnBatch(x,newInboundOrder)).collect(Collectors.toList());
        inboundOrderRepository.save(newInboundOrder);
        batches.forEach(x->batchRepository.save(x));
        return getBatchResponse(inboundOrderDTO);
    }

    /**
     * Este método se encarga de ajustar la respuesta con los campos requeridos para presentar al cliente,
     * este método es invocado desde el método saveBatch.El funcionamiento consiste en un stream que extraé
     * los datos del inboutOrderDto que llega por parametro y transforma las fechas a string para evitar los
     * campos adicionales que ofrece el localdate.
     * @param inboundOrderDTO
     * @return
     */
    //------------------------------------------MÉTODO GETBATCHSTOCK--------------------------------------------------
    private BatchStock getBatchResponse(InboundOrderDTO inboundOrderDTO){
        return new BatchStock(inboundOrderDTO.getBatchStock().stream()
            .map(x-> new BatchResponse(x.getBatchNumber(),x.getProductId(),x.getCurrentTemperature(),x.getMinimumTemperature()
                ,x.getInitialQuantity(),x.getCurrentQuantity(),x.getManufacturingDate().toString(),x.getManufacturingTime().toString(),x.getDueDate().toString()))
            .collect(Collectors.toList()));
    }

    /**
     * método encargado de calcular ir por cada batch enviado por el usuario y sumar el tamaño de cada uno,
     * este calculo es necesario para posteriormente guardar una sección.El método consiste en un stream que mapea
     * el contenido de cada bache y con un getter extraé la cantidad.Finalmente con reduce se realiza la suma.
     * @param inboundOrderDTO
     * @return
     */
    //------------------------------------------MÉTODO GETBATCHSIZE--------------------------------------------------
    private Integer getBatchSize(InboundOrderDTO inboundOrderDTO){
        return  inboundOrderDTO
                .getBatchStock().stream()
                .map(x-> {
                    return x.getCurrentQuantity();
                }).collect(Collectors.toList()).stream().reduce(0,(a,b)->a+b);
    }

    /**
     * Este método se encarga de crear una sección con los datos de un dto que llega por parametro y el respectivo
     * tamaño de todos los baches enviados por el usuario.
     * @param inboundOrderDTO
     * @param sizeBatch
     * @return
     */
    //------------------------------------------MÉTODO SAVESECTION--------------------------------------------------
    private Section saveSection(InboundOrderDTO inboundOrderDTO,Integer sizeBatch){
        var section = new Section(null,inboundOrderDTO.getSection().getSectionCode(),"1",0,0,sizeBatch);
        sectionRepository.save(section);
        return section;
    }

    /**
     * método encargado de regresar un bache con sus respectivos campos requeridos, este método es principalmente
     * invocado desde saveBatch.
     * @param batchDTO
     * @param inboundOrder
     * @return
     */
    //------------------------------------------MÉTODO RETURNBATCH--------------------------------------------------
    private Batch returnBatch(BatchDTO batchDTO,InboundOrder inboundOrder){
        var productTest =productsRepository.findById(batchDTO.getProductId()).orElseThrow(()-> new NonExistentProductException());
        var newBatch = new Batch(null,productTest,batchDTO.getCurrentTemperature(),batchDTO.getMinimumTemperature(),batchDTO.getDueDate(),batchDTO.getManufacturingDate(),batchDTO.getManufacturingTime(),batchDTO.getInitialQuantity(),batchDTO.getCurrentQuantity(),inboundOrder);
        return newBatch;
    }
}
