package com.alura.desafio_forum_hub.domain.topico;

import com.alura.desafio_forum_hub.domain.curso.Curso;
import com.alura.desafio_forum_hub.domain.topico.dto.AtualizarTopicoDTO;
import com.alura.desafio_forum_hub.domain.topico.dto.CriarTopicoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topicos")
@Entity(name = "Topico")
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;

    @Column(name="data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name="ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    @Enumerated(EnumType.STRING)
    private Estado estado;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Topico(CriarTopicoDTO criarTopicoDTO, Usuario usuario, Curso curso) {
        this.titulo = criarTopicoDTO.titulo();
        this.mensagem = criarTopicoDTO.mensagem();
        this.dataCriacao = LocalDateTime.now();
        this.ultimaAtualizacao = LocalDateTime.now();
        this.estado = Estado.OPEN;
        this.usuario = usuario;
        this.curso = curso;
    }

    public void atualizarTopicoComCurso(AtualizarTopicoDTO atualizarTopicoDTO, Curso curso) {
        if (atualizarTopicoDTO.titulo() != null){
            this.titulo = atualizarTopicoDTO.titulo();
        }
        if (atualizarTopicoDTO.mensagem() != null){
            this.mensagem = atualizarTopicoDTO.mensagem();
        }
        if (atualizarTopicoDTO.estado() != null){
            this.estado = atualizarTopicoDTO.estado();
        }
        if (atualizarTopicoDTO.cursoId() != null){
            this.curso = curso;
        }
        this.ultimaAtualizacao = LocalDateTime.now();

    }

    public void atualizarTopico(AtualizarTopicoDTO atualizarTopicoDTO){
        if (atualizarTopicoDTO.titulo() != null){
            this.titulo = atualizarTopicoDTO.titulo();
        }
        if (atualizarTopicoDTO.mensagem() != null){
            this.mensagem = atualizarTopicoDTO.mensagem();
        }
        if(atualizarTopicoDTO.estado() != null){
            this.estado = atualizarTopicoDTO.estado();
        }
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    public void eliminarTopico(){
        this.estado = Estado.DELETED;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

}
