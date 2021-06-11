package com.mercadolibre.demo_bootcamp_spring.services.Batch;

import com.mercadolibre.demo_bootcamp_spring.dtos.*;

import javax.validation.Valid;
import java.util.List;

public interface IBatchService {
    BatchStock saveBatch(@Valid InboundOrderTransaction inboundOrderDTO);
    BatchStock putBatch(InboundOrderTransaction inboundOrder);
    BatchStockProductSearch getProductFromBatches(String idProducto,String ordBy);
}
