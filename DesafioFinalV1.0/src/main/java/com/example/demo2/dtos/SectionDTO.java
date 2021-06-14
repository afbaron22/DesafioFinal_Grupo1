package com.example.demo2.dtos;

import com.example.demo2.models.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SectionDTO {


    private @Valid State sectionCode;

    @NotBlank(message = "Warehouse Code cant be empty")
    private String warehouseCode;
}
