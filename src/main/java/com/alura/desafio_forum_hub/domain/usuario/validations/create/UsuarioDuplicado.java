package com.alura.desafio_forum_hub.domain.usuario.validations.create;

import com.alura.desafio_forum_hub.domain.usuario.dto.CriarUsuarioDTO;
import com.alura.desafio_forum_hub.domain.usuario.repository.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDuplicado implements ValidarCriarUsuario{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CriarUsuarioDTO dados) {
        var usuarioDuplicado = repository.findByUsername(dados.username());
        if(usuarioDuplicado != null){
            throw new ValidationException("Este usuário já existe.");
        }

        var emailDuplicado = repository.findByEmail(dados.email());
        if(emailDuplicado != null){
            throw new ValidationException("Este e-mail já existe.");
        }
    }

}
