package com.mercadolibre.demo_bootcamp_spring.dtos;

import com.mercadolibre.demo_bootcamp_spring.models.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

@Data
@AllArgsConstructor
public class orderStatusDTO {
    StatusCode statusCode;
}
