package com.example.demo2.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class InboundOrderDTO {

    @NotBlank(message = "Order Number is required")
    private String orderNumber;
    @NotNull(message = "Order Date  is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Argentina/Buenos_Aires")
    private LocalDate orderDate;


    private @Valid SectionDTO section;

    private List<@Valid BatchDTO> batchStock;

}
