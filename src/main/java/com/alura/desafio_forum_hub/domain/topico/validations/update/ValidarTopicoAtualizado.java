package com.alura.desafio_forum_hub.domain.topico.validations.update;

import com.alura.desafio_forum_hub.domain.topico.dto.AtualizarTopicoDTO;

public interface ValidarTopicoAtualizado {

    void validate(AtualizarTopicoDTO data);

}
