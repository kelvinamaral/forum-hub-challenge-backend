package com.alura.desafio_forum_hub.domain.usuario.validations.update;

import com.alura.desafio_forum_hub.domain.usuario.dto.AtualizarUsuarioDTO;
import com.alura.desafio_forum_hub.domain.usuario.repository.UsuarioRepository;
import com.alura.desafio_forum_hub.domain.usuario.validations.create.ValidarCriarUsuario;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidarAtualizacaoUsuario implements ValidarCriarUsuario {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(AtualizarUsuarioDTO dados) {
        if (dados.email() != null) {
            var emailDuplicado = repository.findByEmail(dados.email());
            if (emailDuplicado != null) {
                throw new ValidationException("Este e-mail já está em uso.");
            }
        }
    }


}
