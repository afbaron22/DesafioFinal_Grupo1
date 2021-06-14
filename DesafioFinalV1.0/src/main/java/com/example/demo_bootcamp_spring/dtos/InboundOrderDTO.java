package com.example.demo_bootcamp_spring.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private @Valid SectionDTO section;

    private List<@Valid BatchDTO> batchStock;

}
