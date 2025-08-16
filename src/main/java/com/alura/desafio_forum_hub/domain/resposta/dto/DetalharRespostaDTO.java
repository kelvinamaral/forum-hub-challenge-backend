package com.alura.desafio_forum_hub.domain.resposta.dto;

import com.alura.desafio_forum_hub.domain.resposta.Resposta;

import java.time.LocalDateTime;

public record DetalharRespostaDTO(
        Long id,
        String mensagem,
        LocalDateTime dataCriacao,
        LocalDateTime ultimaAtualizacao,
        Boolean solucao,
        Boolean apagado,
        Long usuarioId,
        String username,
        Long topicoId,
        String topico
) {

    public DetalharRespostaDTO(Resposta resposta){
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getUltimaAtualizacao(),
                resposta.getSolucao(),
                resposta.getApagado(),
                resposta.getUsuario().getId(),
                resposta.getUsuario().getUsername(),
                resposta.getTopico().getId(),
                resposta.getTopico().getTitulo());
    }
}
