package com.alura.desafio_forum_hub.domain.curso.dto;

import com.alura.desafio_forum_hub.domain.curso.Categoria;
import com.alura.desafio_forum_hub.domain.curso.Curso;

public record DetalharCursoDTO(Long id, String nome, Categoria categoria, Boolean ativo) {

    public DetalharCursoDTO(Curso curso){
        this(
                curso.getId(),
                curso.getNome(),
                curso.getCategoria(),
                curso.getAtivo()
        );
    }

}
