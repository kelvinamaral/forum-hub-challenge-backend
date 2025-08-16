package com.alura.desafio_forum_hub.domain.curso.dto;

import com.alura.desafio_forum_hub.domain.curso.Categoria;

public record AtualizarCursoDTO(String nome,
                                Categoria categoria,
                                Boolean ativo) {

}
