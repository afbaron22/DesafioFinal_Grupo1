package com.mercadolibre.demo_bootcamp_spring.services.Batch;

import com.mercadolibre.demo_bootcamp_spring.dtos.BatchStock;
import com.mercadolibre.demo_bootcamp_spring.dtos.InboundOrderDTO;
import com.mercadolibre.demo_bootcamp_spring.dtos.BatchResponse;
import com.mercadolibre.demo_bootcamp_spring.dtos.InboundOrderTransaction;

import javax.validation.Valid;
import java.util.List;

public interface IBatchService {
    BatchStock saveBatch(@Valid InboundOrderTransaction inboundOrderDTO);
}
