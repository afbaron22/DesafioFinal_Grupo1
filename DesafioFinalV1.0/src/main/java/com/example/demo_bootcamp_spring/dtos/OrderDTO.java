package com.example.demo_bootcamp_spring.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Argentina/Buenos_Aires")
    LocalDate date;
    String buyerId;
    List<ProductDTO> products;
}
