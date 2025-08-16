package com.alura.desafio_forum_hub.domain.topico.validations.create;

import com.alura.desafio_forum_hub.domain.curso.repository.CursoRepository;
import com.alura.desafio_forum_hub.domain.topico.dto.CriarTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCursoCriado implements ValidarTopicoCriado {

    @Autowired
    private CursoRepository repository;

    @Override
    public void validate(CriarTopicoDTO dados) {
        var cursoExiste = repository.existsById(dados.cursoId());
        if (!cursoExiste) {
            throw new ValidationException("Este curso não existe.");
        }

        var cursoHabilitado = repository.findById(dados.cursoId()).get().getAtivo();
        if (!cursoHabilitado) {
            throw new ValidationException("Este curso não está disponível no momento.");
        }
    }
}
