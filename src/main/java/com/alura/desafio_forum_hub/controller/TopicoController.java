package com.alura.desafio_forum_hub.controller;

import com.alura.desafio_forum_hub.domain.curso.Curso;
import com.alura.desafio_forum_hub.domain.curso.repository.CursoRepository;
import com.alura.desafio_forum_hub.domain.resposta.Resposta;
import com.alura.desafio_forum_hub.domain.resposta.dto.DetalhesRespostaDTO;
import com.alura.desafio_forum_hub.domain.resposta.repository.RespostaRepository;
import com.alura.desafio_forum_hub.domain.topico.Estado;
import com.alura.desafio_forum_hub.domain.topico.Topico;
import com.alura.desafio_forum_hub.domain.topico.dto.AtualizarTopicoDTO;
import com.alura.desafio_forum_hub.domain.topico.dto.CriarTopicoDTO;
import com.alura.desafio_forum_hub.domain.topico.dto.DetalhesTopicoDTO;
import com.alura.desafio_forum_hub.domain.topico.repository.TopicoRepository;
import com.alura.desafio_forum_hub.domain.topico.validations.create.ValidarTopicoCriado;
import com.alura.desafio_forum_hub.domain.topico.validations.update.ValidarTopicoAtualizado;
import com.alura.desafio_forum_hub.domain.usuario.Usuario;
import com.alura.desafio_forum_hub.domain.usuario.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Tópico", description = "Está vinculado a um curso e usuário específicos.")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    List<ValidarTopicoCriado> criarValidadores;

    @Autowired
    List<ValidarTopicoAtualizado> atualizarValidadores;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra um novo tópico no banco de dados")
    public ResponseEntity<DetalhesTopicoDTO> criarTopico(@RequestBody @Valid CriarTopicoDTO criarTopicoDTO,
                                                         UriComponentsBuilder uriBuilder) {
        criarValidadores.forEach(v -> v.validate(criarTopicoDTO));

        Usuario usuario = usuarioRepository.findById(criarTopicoDTO.usuarioId()).get();
        Curso curso = cursoRepository.findById(criarTopicoDTO.cursoId()).get();
        Topico topico = new Topico(criarTopicoDTO, usuario, curso);

        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesTopicoDTO(topico));
    }

    @GetMapping("/all")
    @Operation(summary = "Lê todos os tópicos, independentemente de seu estado")
    public ResponseEntity<Page<DetalhesTopicoDTO>> lerTodosTopicos(@PageableDefault(size = 5, sort = {"ultimaAtualizacao"}, direction = Sort.Direction.DESC) Pageable pageable) {
        var pagina = topicoRepository.findAll(pageable).map(DetalhesTopicoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping
    @Operation(summary = "Lista tópicos abertos e fechados")
    public ResponseEntity<Page<DetalhesTopicoDTO>> lerTopicosNaoDeletados(@PageableDefault(size = 5, sort = {"ultimaAtualizacao"}, direction = Sort.Direction.DESC) Pageable pageable) {
        var pagina = topicoRepository.findAllByEstadoIsNot(Estado.DELETED, pageable).map(DetalhesTopicoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lê um único tópico por seu ID")
    public ResponseEntity<DetalhesTopicoDTO> lerUmTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        var dadosTopico = new DetalhesTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getUltimaAtualizacao(),
                topico.getEstado(),
                topico.getUsuario().getUsername(),
                topico.getCurso().getNome(),
                topico.getCurso().getCategoria()
        );
        return ResponseEntity.ok(dadosTopico);
    }

    @GetMapping("/{id}/solucao")
    @Operation(summary = "Lê a resposta do tópico marcada como sua solução")
    public ResponseEntity<DetalhesRespostaDTO> lerSolucaoTopico(@PathVariable Long id) {
        Resposta resposta = respostaRepository.getReferenceByTopicoIdAndSolucaoTrue(id);
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
    @Operation(summary = "Atualiza o título, a mensagem, o estado ou o ID do curso de um tópico")
    public ResponseEntity<DetalhesTopicoDTO> atualizarTopico(@RequestBody @Valid AtualizarTopicoDTO atualizarTopicoDTO, @PathVariable Long id) {
        atualizarValidadores.forEach(v -> v.validate(atualizarTopicoDTO));

        Topico topico = topicoRepository.getReferenceById(id);

        if (atualizarTopicoDTO.cursoId() != null) {
            Curso curso = cursoRepository.getReferenceById(atualizarTopicoDTO.cursoId());
            topico.atualizarTopicoComCurso(atualizarTopicoDTO, curso);
        } else {
            topico.atualizarTopico(atualizarTopicoDTO);
        }

        var dadosTopico = new DetalhesTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getUltimaAtualizacao(),
                topico.getEstado(),
                topico.getUsuario().getUsername(),
                topico.getCurso().getNome(),
                topico.getCurso().getCategoria()
        );
        return ResponseEntity.ok(dadosTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Exclui um tópico")
    public ResponseEntity<?> excluirTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.eliminarTopico();
        return ResponseEntity.noContent().build();
    }
}