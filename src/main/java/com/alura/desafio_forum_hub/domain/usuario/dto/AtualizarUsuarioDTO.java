package com.alura.desafio_forum_hub.domain.usuario.dto;

import com.alura.desafio_forum_hub.domain.usuario.Role;

public record AtualizarUsuarioDTO(
        String password,
        Role role,
        String nome,
        String sobrenome,
        String email,
        Boolean enabled
) {
}
