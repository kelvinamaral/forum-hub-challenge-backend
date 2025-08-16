package com.alura.desafio_forum_hub.domain.usuario.validations.update;

import com.alura.desafio_forum_hub.domain.usuario.dto.AtualizarUsuarioDTO;

public interface ValidarAtualizarUsuario {
    void validate(AtualizarUsuarioDTO data);
}
