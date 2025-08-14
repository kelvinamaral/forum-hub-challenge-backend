package com.alura.desafio_forum_hub.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @GetMapping
    public String topicos(){
        return "Teste de página de tópicos!";
    }


}
