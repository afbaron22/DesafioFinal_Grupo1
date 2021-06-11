package com.mercadolibre.demo_bootcamp_spring.dtos;


import com.mercadolibre.demo_bootcamp_spring.models.Batch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class InboundOrderDTO {

    @NotNull(message = "Order Number is required")
    private String orderNumber;
    @NotNull(message = "Order Date  is required")
    private LocalDate orderDate;
    @Valid
    private SectionDTO section;
    private List<@Valid BatchDTO> batchStock;

}
