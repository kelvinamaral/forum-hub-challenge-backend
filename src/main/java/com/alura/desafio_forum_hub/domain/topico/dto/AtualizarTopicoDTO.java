package com.alura.desafio_forum_hub.domain.topico.dto;

import com.alura.desafio_forum_hub.domain.topico.Estado;

public record AtualizarTopicoDTO(
        String titulo,
        String mensagem,
        Estado estado,
        Long cursoId
) {
}
