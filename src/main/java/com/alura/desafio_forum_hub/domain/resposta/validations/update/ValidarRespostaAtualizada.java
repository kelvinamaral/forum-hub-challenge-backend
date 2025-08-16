package com.alura.desafio_forum_hub.domain.resposta.validations.update;

import com.alura.desafio_forum_hub.domain.resposta.dto.AtualizarRespostaDTO;

public interface ValidarRespostaAtualizada {

    void validate(AtualizarRespostaDTO data, Long respostaId);

}
