package com.example.demo_bootcamp_spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class InboundOrderTransaction {
    @NotNull(message = "Initial Quantity is required")
    private @Valid InboundOrderDTO inboundOrder;
}
