package com.alura.desafio_forum_hub.domain.resposta.validations.create;

import com.alura.desafio_forum_hub.domain.resposta.dto.CriarRespostaDTO;
import com.alura.desafio_forum_hub.domain.usuario.repository.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class RespostaUsuarioValida implements ValidarRespostaCriada {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CriarRespostaDTO dados) {
        var usuarioExiste = repository.existsById(dados.usuarioId());

        if (!usuarioExiste) {
            throw new ValidationException("Este usuário não existe.");
        }

        var usuarioHabilitado = repository.findById(dados.usuarioId()).get().isEnabled();

        if (!usuarioHabilitado) {
            throw new ValidationException("Este usuário não está habilitado.");
        }
    }
}
