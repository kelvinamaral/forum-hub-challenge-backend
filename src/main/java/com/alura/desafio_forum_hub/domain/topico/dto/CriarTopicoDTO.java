package com.alura.desafio_forum_hub.domain.topico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarTopicoDTO(
        @NotBlank String titulo,
        @NotBlank String mensagem,
        @NotNull Long usuarioId,
        @NotNull Long cursoId
) {
}
