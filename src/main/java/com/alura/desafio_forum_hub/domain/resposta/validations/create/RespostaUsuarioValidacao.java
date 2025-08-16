package com.alura.desafio_forum_hub.domain.resposta.validations.create;

import com.alura.desafio_forum_hub.domain.resposta.dto.CriarRespostaDTO;
import jakarta.validation.ValidationException;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;

public class RespostaUsuarioValidacao implements ValidarRespostaCriada {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CriarRespostaDTO data){
        var usuarioExiste = repository.existsById(data.usuarioId());
    }

    if(!usuarioExiste){
        throw new ValidationException("Este usuario não existe!");
    }

    var usuarioHabilitado = repository.findById(data.usuarioId()).get().isEnabled();

        if(!usuarioHabilitado){
        throw new ValidationException("Este usuario não esta habilitado");
    }

}
