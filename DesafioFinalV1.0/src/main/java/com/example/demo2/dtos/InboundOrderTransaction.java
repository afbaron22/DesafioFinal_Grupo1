package com.example.demo2.dtos;

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
    @NotNull(message = "Inbound Order is required")
    private @Valid InboundOrderDTO inboundOrder;
}
