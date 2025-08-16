package com.alura.desafio_forum_hub.domain.resposta.validations.create;

import com.alura.desafio_forum_hub.domain.resposta.dto.AtualizarRespostaDTO;
import com.alura.desafio_forum_hub.domain.resposta.dto.CriarRespostaDTO;
import com.alura.desafio_forum_hub.domain.topico.Estado;
import com.alura.desafio_forum_hub.domain.topico.repository.TopicoRepository;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespostaTopicoValidacao implements ValidarRespostaCriada {

    @Autowired
    private TopicoRepository repository;

    @Override
    public void validate(CriarRespostaDTO data){
        var topicoExistente = repository.existsById(data.topicoId());

        if (!topicoExistente){
            throw new ValidationException("Este topico não existe!");
        }

        var topicoAberto = repository.findById(data.topicoId()).get().getEstado();

        if (topicoAberto != Estado.OPEN){
            throw new RuntimeException("Este tópico não está aberto!");
        }
    }

}
