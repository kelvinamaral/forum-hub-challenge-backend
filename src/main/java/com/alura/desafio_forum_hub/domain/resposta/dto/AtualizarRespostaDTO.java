package com.alura.desafio_forum_hub.domain.resposta.dto;

public record AtualizarRespostaDTO(
        String mensagem,
        Boolean solucao,
        Boolean apagado

) {
}
