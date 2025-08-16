package com.alura.desafio_forum_hub.domain.resposta.validations.create;

import com.alura.desafio_forum_hub.domain.resposta.dto.CriarRespostaDTO;

public interface ValidarRespostaCriada {
    void validate(CriarRespostaDTO data);
}
