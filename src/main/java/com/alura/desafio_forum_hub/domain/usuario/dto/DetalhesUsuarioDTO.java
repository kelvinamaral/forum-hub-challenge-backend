package com.alura.desafio_forum_hub.domain.usuario.dto;

import com.alura.desafio_forum_hub.domain.usuario.Role;

public record DetalhesUsuarioDTO(Long id,
                                 String username,
                                 Role role,
                                 String nome,
                                 String sobrenome,
                                 String email,
                                 Boolean enabled) {
}
