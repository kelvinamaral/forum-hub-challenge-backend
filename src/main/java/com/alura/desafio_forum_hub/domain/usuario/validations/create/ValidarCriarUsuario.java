package com.alura.desafio_forum_hub.domain.usuario.validations.create;

import com.alura.desafio_forum_hub.domain.usuario.dto.CriarUsuarioDTO;

public interface ValidarCriarUsuario {
    void validate(CriarUsuarioDTO data);
}
