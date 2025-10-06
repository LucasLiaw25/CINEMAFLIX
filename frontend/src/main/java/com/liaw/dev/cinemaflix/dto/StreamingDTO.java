package com.liaw.dev.cinemaflix.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreamingDTO {
    private Long id;

    @NotEmpty(message = "Nome de categoria vazio")
    private String name;
}
