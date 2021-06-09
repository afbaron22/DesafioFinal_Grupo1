package com.mercadolibre.demo_bootcamp_spring.services.Batch;

import com.mercadolibre.demo_bootcamp_spring.dtos.InboundOrderDTO;
import com.mercadolibre.demo_bootcamp_spring.models.Batch;
import com.mercadolibre.demo_bootcamp_spring.models.InboundOrder;

import java.util.List;

public interface IBatchService {
    List<Batch> saveBatch(InboundOrderDTO inboundOrderDTO);
}
