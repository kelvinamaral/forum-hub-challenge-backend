package com.alura.desafio_forum_hub.domain.topico.validations.create;

import com.alura.desafio_forum_hub.domain.topico.dto.CriarTopicoDTO;
import com.alura.desafio_forum_hub.domain.topico.repository.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoDuplicado implements ValidarTopicoCriado {

    @Autowired
    private TopicoRepository topicoRepository;


    @Override
    public void validate(CriarTopicoDTO dados) {
        var topicoDuplicado = topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem());
        if(topicoDuplicado){
            throw new ValidationException("Este tópico já existe. Verifique em /topicos/" + topicoRepository.findByTitulo(dados.titulo()).getId());

        }
    }
}