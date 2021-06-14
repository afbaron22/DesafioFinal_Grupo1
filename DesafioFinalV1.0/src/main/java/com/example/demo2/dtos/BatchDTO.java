package com.example.demo2.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchDTO {

    @NotBlank(message = "Batch Number is required")
    @Pattern(regexp = "[0-9]*")
    private String batchNumber;

    @NotBlank(message = "Product Id cant be empty")
    @Pattern(regexp = "[0-9]*")
    private String productId;

    @NotNull(message = "Current Temperature is required")
    private Float currentTemperature;

    @NotNull(message = "Minimum Temperature is required")
    private Float minimumTemperature;

    @NotNull(message = "Initial Quantity is required")
    private Integer initialQuantity;

    @NotNull(message = "Current Quantity is required")
    private Integer currentQuantity;

    @NotNull(message = "Manufacturing Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
   // @Pattern(regexp = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Argentina/Buenos_Aires")
    private LocalDate manufacturingDate;

    @NotNull(message = "Manufacturing Time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private LocalDateTime manufacturingTime;

    @NotNull(message = "Due Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Argentina/Buenos_Aires")
    private LocalDate dueDate;


}
