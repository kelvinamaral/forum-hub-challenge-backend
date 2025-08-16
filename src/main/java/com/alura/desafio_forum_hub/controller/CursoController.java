package com.alura.desafio_forum_hub.controller;

import com.alura.desafio_forum_hub.domain.curso.Curso;
import com.alura.desafio_forum_hub.domain.curso.dto.AtualizarCursoDTO;
import com.alura.desafio_forum_hub.domain.curso.dto.CriarCursoDTO;
import com.alura.desafio_forum_hub.domain.curso.dto.DetalharCursoDTO;
import com.alura.desafio_forum_hub.domain.curso.repository.CursoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Curso" , description = "Gerencia operações relacionadas a cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @PostMapping
    @Transactional
    @Operation(summary = "Regista um novo curso no banco de dados.")
    public ResponseEntity<DetalharCursoDTO> criarTopico(@RequestBody @Valid CriarCursoDTO criarCursoDTO,
                                                        UriComponentsBuilder uriBuilder){
        Curso curso = new Curso(criarCursoDTO);
        repository.save(curso);
        var uri = uriBuilder.path("/cursos/{i}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalharCursoDTO(curso));

    }

    @GetMapping("/all")
    @Operation(summary = "Faz a leitura de todos os cursos independente do estado")
    public ResponseEntity<Page<DetalharCursoDTO>> listarCursos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = repository.findAll(pageable).map(DetalharCursoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping
    @Operation(summary = "Lista de cursos ativos")
    public ResponseEntity<Page<DetalharCursoDTO>> listarCursosAtivos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = repository.findAllByAtivoTrue(pageable).map(DetalharCursoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Faz a leitura de apenas um curso pelo ID")

    public ResponseEntity<DetalharCursoDTO> ListarUmCurso(@PathVariable Long id){
        Curso curso = repository.getReferenceById(id);
        var dadosDoCurso = new DetalharCursoDTO(
                curso.getId(),
                curso.getNome(),
                curso.getCategoria(),
                curso.getAtivo()
        );
        return ResponseEntity.ok(dadosDoCurso);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualiza o nome, categoria ou status de um curso")
    public ResponseEntity<DetalharCursoDTO> atualizarCurso(@RequestBody @Valid AtualizarCursoDTO atualizarCursoDTO, @PathVariable Long id){

        Curso curso = repository.getReferenceById(id);

        curso.atualizarCurso(atualizarCursoDTO);

        var dadosDoCurso = new DetalharCursoDTO(
                curso.getId(),
                curso.getNome(),
                curso.getCategoria(),
                curso.getAtivo()
        );
        return ResponseEntity.ok(dadosDoCurso);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Exclui um curso")
    public ResponseEntity<?> eliminarCurso(@PathVariable Long id){
        Curso curso = repository.getReferenceById(id);
        curso.eliminarCurso();
        return ResponseEntity.noContent().build();
    }

}
