package com.alura.desafio_forum_hub.domain.topico.dto;

import com.alura.desafio_forum_hub.domain.curso.Categoria;
import com.alura.desafio_forum_hub.domain.topico.Estado;
import com.alura.desafio_forum_hub.domain.topico.Topico;

import java.time.LocalDateTime;

public record DetalhesTopicoDTO(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        LocalDateTime ultimaAtualizacao,
        Estado estado,
        String usuario,
        String curso,
        Categoria categoriaCurso
) {
    public DetalhesTopicoDTO(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getUltimaAtualizacao(),
                topico.getEstado(),
                topico.getUsuario().getUsername(),
                topico.getCurso().getNome(),
                topico.getCurso().getCategoria()
        );
    }
}
