package com.mercadolibre.demo_bootcamp_spring.dtos;


import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Validated
@Data
public class InboundOrderDTO {

    @NotNull(message = "Order Number is required")
    private Integer orderNumber;
    @NotNull(message = "Order Date  is required")
    private LocalDate orderDate;
    @Valid
    private SectionDTO section;
    @Valid
    private List<BatchDTO> batchStock;


}
