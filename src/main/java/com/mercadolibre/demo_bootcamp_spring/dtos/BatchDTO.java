package com.mercadolibre.demo_bootcamp_spring.dtos;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Validated
@Data
public class BatchDTO {

    @NotNull(message = "Batch Number is required")
    private Integer batchNumber;

    @NotBlank(message = "Product Id cant be empty")
    @Size(min = 3, max = 45, message = "The length of Product Id must be between 3 and 45 characters")
    private String productId;

    @NotNull(message = "Current Temperatura is required")
    private Float currentTemperature;

    @NotNull(message = "Minimum Temperatura is required")
    private Float minimumTemperature;

    @NotNull(message = "Initial Quantity is required")
    private Integer initialQuantity;

    @NotNull(message = "Current Quantity is required")
    private Integer currentQuantity;

    @NotNull(message = "Manufacturing Date is required")
    private LocalDate manufacturingDate;

    @NotNull(message = "Manufacturing Time is required")
    private LocalDateTime manufacturingTime;

    @NotNull(message = "Due Date is required")
    private LocalDateTime dueDate;


}
