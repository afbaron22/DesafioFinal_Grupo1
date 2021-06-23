package com.example.demo_bootcamp_spring.dtos;

import com.example.demo_bootcamp_spring.models.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SectionDTO {

    @NotNull(message = "Section Code cant be empty")
    private State sectionCode;

    @NotBlank(message = "Warehouse Code cant be empty")
    private String warehouseCode;
}
