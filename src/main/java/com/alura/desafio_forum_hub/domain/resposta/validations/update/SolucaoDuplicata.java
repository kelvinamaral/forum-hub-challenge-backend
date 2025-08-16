package com.alura.desafio_forum_hub.domain.resposta.validations.update;

import com.alura.desafio_forum_hub.domain.resposta.Resposta;
import com.alura.desafio_forum_hub.domain.resposta.dto.AtualizarRespostaDTO;
import com.alura.desafio_forum_hub.domain.resposta.repository.RespostaRepository;
import com.alura.desafio_forum_hub.domain.topico.Estado;
import com.alura.desafio_forum_hub.domain.topico.repository.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolucaoDuplicata implements ValidarRespostaAtualizada {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validate(AtualizarRespostaDTO data, Long respostaId) {
        if (data.solucao()) {
            Resposta resposta = respostaRepository.getReferenceById(respostaId);
            var topicoResolvido = topicoRepository.getReferenceById(resposta.getTopico().getId());
            if (topicoResolvido.getEstado() == Estado.CLOSED) {
                throw new ValidationException("Este tópico está solucionado.");
            }
        }
    }
}
