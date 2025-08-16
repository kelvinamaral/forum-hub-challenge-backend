package com.alura.desafio_forum_hub.domain.topico.validations.update;

import com.alura.desafio_forum_hub.domain.curso.repository.CursoRepository;
import com.alura.desafio_forum_hub.domain.topico.dto.AtualizarTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCursoAtualizado implements ValidarTopicoAtualizado {

    @Autowired
    private CursoRepository repository;

    @Override
    public void validate(AtualizarTopicoDTO dados) {
        if (dados.cursoId() != null) {
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
}
