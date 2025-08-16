package com.alura.desafio_forum_hub.controller;

import com.alura.desafio_forum_hub.domain.usuario.Usuario;
import com.alura.desafio_forum_hub.domain.usuario.dto.AutenticacaoUsuarioDTO;
import com.alura.desafio_forum_hub.infra.security.dto.JWTtokenDTO;
import com.alura.desafio_forum_hub.infra.service.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticação", description = "Obtém o token do usuário designado para o acesso.")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<JWTtokenDTO> autenticarUsuario(@RequestBody @Valid AutenticacaoUsuarioDTO autenticacaoUsuario){
        Authentication authToken = new UsernamePasswordAuthenticationToken(autenticacaoUsuario.username(),
                autenticacaoUsuario.password());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.gerarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new JWTtokenDTO(JWTtoken));
    }
}
