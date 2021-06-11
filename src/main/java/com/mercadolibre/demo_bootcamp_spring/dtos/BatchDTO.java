package com.mercadolibre.demo_bootcamp_spring.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchDTO {

    @NotNull(message = "Batch Number is required")
    private String batchNumber;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Argentina/Buenos_Aires")
    private LocalDate manufacturingDate;

    @NotNull(message = "Manufacturing Time is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private LocalDateTime manufacturingTime;

    @NotNull(message = "Due Date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Argentina/Buenos_Aires")
    private LocalDate dueDate;



}
