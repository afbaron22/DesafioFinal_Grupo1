package com.example.demo_bootcamp_spring.services.Batch;

import com.example.demo_bootcamp_spring.dtos.*;

import javax.validation.Valid;

public interface IBatchService {
    BatchStock saveBatch(@Valid InboundOrderTransaction inboundOrderDTO);
    BatchStock putBatch(InboundOrderTransaction inboundOrder);
    BatchStockProductSearch getProductFromBatches(String idProducto,String ordBy);
    SearchedWarehouseProducts getProductFromWarehouses(String idProducto);

}
