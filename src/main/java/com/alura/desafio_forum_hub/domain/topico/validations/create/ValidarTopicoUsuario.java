package com.alura.desafio_forum_hub.domain.topico.validations.create;

import com.alura.desafio_forum_hub.domain.topico.dto.CriarTopicoDTO;
import com.alura.desafio_forum_hub.domain.usuario.repository.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarTopicoUsuario implements ValidarTopicoCriado {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CriarTopicoDTO dados) {
        var usuarioExiste = repository.existsById(dados.usuarioId());
        if (!usuarioExiste) {
            throw new ValidationException("Este usuário não existe.");
        }

        var usuarioHabilitado = repository.findById(dados.usuarioId()).get().getAtivo();
        if (!usuarioHabilitado) {
            throw new ValidationException("Este usuário foi desabilitado.");
        }
    }
}
