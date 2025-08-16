package com.alura.desafio_forum_hub.controller;

import com.alura.desafio_forum_hub.domain.resposta.Resposta;
import com.alura.desafio_forum_hub.domain.resposta.dto.AtualizarRespostaDTO;
import com.alura.desafio_forum_hub.domain.resposta.dto.CriarRespostaDTO;
import com.alura.desafio_forum_hub.domain.resposta.dto.DetalhesRespostaDTO;
import com.alura.desafio_forum_hub.domain.resposta.repository.RespostaRepository;
import com.alura.desafio_forum_hub.domain.resposta.validations.create.ValidarRespostaCriada;
import com.alura.desafio_forum_hub.domain.resposta.validations.update.ValidarRespostaAtualizada;
import com.alura.desafio_forum_hub.domain.topico.Estado;
import com.alura.desafio_forum_hub.domain.topico.Topico;
import com.alura.desafio_forum_hub.domain.topico.repository.TopicoRepository;
import com.alura.desafio_forum_hub.domain.usuario.Usuario;
import com.alura.desafio_forum_hub.domain.usuario.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/respostas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Respostas", description = "Apenas uma pode ser a solução para o tópico.")
public class RespostaController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    List<ValidarRespostaCriada> criarValidadores;

    @Autowired
    List<ValidarRespostaAtualizada> atualizarValidadores;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra uma nova resposta na base de dados, vinculada a um usuário e tópico existentes.")
    public ResponseEntity<DetalhesRespostaDTO> criarResposta(@RequestBody @Valid CriarRespostaDTO criarRespostaDTO,
                                                             UriComponentsBuilder uriBuilder) {
        criarValidadores.forEach(v -> v.validate(criarRespostaDTO));

        Usuario usuario = usuarioRepository.getReferenceById(criarRespostaDTO.usuarioId());
        Topico topico = topicoRepository.findById(criarRespostaDTO.topicoId()).get();

        var resposta = new Resposta(criarRespostaDTO, usuario, topico);
        respostaRepository.save(resposta);

        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesRespostaDTO(resposta));
    }

    @GetMapping("/topico/{topicoId}")
    @Operation(summary = "Lê todas as respostas de um tópico")
    public ResponseEntity<Page<DetalhesRespostaDTO>>
    lerRespostasDeTopico(@PageableDefault(size = 5, sort = {"ultimaAtualizacao"},
            direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Long topicoId) {
        var pagina = respostaRepository.findAllByTopicoId(topicoId, pageable).map(DetalhesRespostaDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lê todas as respostas de um usuário.")
    public ResponseEntity<Page<DetalhesRespostaDTO>>
    lerRespostasDeUsuarios(@PageableDefault(size = 5, sort = {"ultimaAtualizacao"},
            direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Long usuarioId) {
        var pagina = respostaRepository.findAllByUsuarioId(usuarioId, pageable).map(DetalhesRespostaDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lê uma única resposta por seu ID")
    public ResponseEntity<DetalhesRespostaDTO> lerUmaResposta(@PathVariable Long id) {
        Resposta resposta = respostaRepository.getReferenceById(id);

        var dadosResposta = new DetalhesRespostaDTO(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getUltimaAtualizacao(),
                resposta.getSolucao(),
                resposta.getApagado(),
                resposta.getUsuario().getId(),
                resposta.getUsuario().getUsername(),
                resposta.getTopico().getId(),
                resposta.getTopico().getTitulo()
        );
        return ResponseEntity.ok(dadosResposta);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualiza a mensagem, a solução ou o estado de uma resposta.")
    public ResponseEntity<DetalhesRespostaDTO> atualizarResposta(@RequestBody @Valid AtualizarRespostaDTO atualizarRespostaDTO, @PathVariable Long id) {
        atualizarValidadores.forEach(v -> v.validate(atualizarRespostaDTO, id));
        Resposta resposta = respostaRepository.getReferenceById(id);
        resposta.atualizarResposta(atualizarRespostaDTO);

        if (atualizarRespostaDTO.solucao()) {
            var topicoResolvido = topicoRepository.getReferenceById(resposta.getTopico().getId());
            topicoResolvido.setEstado(Estado.CLOSED);
        }

        var dadosResposta = new DetalhesRespostaDTO(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getUltimaAtualizacao(),
                resposta.getSolucao(),
                resposta.getApagado(),
                resposta.getUsuario().getId(),
                resposta.getUsuario().getUsername(),
                resposta.getTopico().getId(),
                resposta.getTopico().getTitulo()
        );
        return ResponseEntity.ok(dadosResposta);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Exclui uma resposta por seu ID")
    public ResponseEntity<?> excluirResposta(@PathVariable Long id) {
        Resposta resposta = respostaRepository.getReferenceById(id);
        resposta.eliminarResposta();
        return ResponseEntity.noContent().build();
    }
}
