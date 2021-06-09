package com.mercadolibre.demo_bootcamp_spring.services.Batch;

import com.mercadolibre.demo_bootcamp_spring.dtos.InboundOrderDTO;
import com.mercadolibre.demo_bootcamp_spring.models.InboundOrder;

public interface IBatchService {
    InboundOrder saveBatch(InboundOrderDTO inboundOrderDTO);
}
