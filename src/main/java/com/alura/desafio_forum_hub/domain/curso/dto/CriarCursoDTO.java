package com.alura.desafio_forum_hub.domain.curso.dto;

import com.alura.desafio_forum_hub.domain.curso.Categoria;
import jakarta.validation.constraints.NotBlank;

public record CriarCursoDTO(
        @NotBlank String nome,
        @NotBlank Categoria categoria
        ) {
}
