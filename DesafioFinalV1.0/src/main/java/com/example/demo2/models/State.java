package com.example.demo2.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NotNull
public enum State {
    FS,
    RF,
    FF
}
