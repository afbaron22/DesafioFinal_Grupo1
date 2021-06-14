package com.example.demo2.services.Batch;

import com.example.demo2.dtos.BatchStock;
import com.example.demo2.dtos.BatchStockProductSearch;
import com.example.demo2.dtos.InboundOrderTransaction;
import com.example.demo2.dtos.SearchedWarehouseProducts;

import javax.validation.Valid;

public interface IBatchService {
    BatchStock saveBatch(@Valid InboundOrderTransaction inboundOrderDTO);

    BatchStock putBatch(@Valid InboundOrderTransaction inboundOrder);

    BatchStockProductSearch getProductFromBatches(String idProducto, String ordBy);

    SearchedWarehouseProducts getProductFromWarehouses(String idProducto);
}
