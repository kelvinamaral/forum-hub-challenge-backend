package com.alura.desafio_forum_hub.domain.resposta;

import com.alura.desafio_forum_hub.domain.resposta.dto.AtualizarRespostaDTO;
import com.alura.desafio_forum_hub.domain.resposta.dto.CriarRespostaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "curso")
@Entity(name = "Curso")
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;

    @Column(name="data_Criacao")
    private LocalDateTime dataCriacao;

    @Column(name="ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    private Boolean solucao;
    private Boolean apagado;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topico_id")
    private Topico topico;

    public Resposta(CriarRespostaDTO criarRespostaDTO, Usuario usuario, Topico topico) {
        this.mensagem = criarRespostaDTO.mensagem();
        this.dataCriacao = LocalDateTime.now();
        this.ultimaAtualizacao = LocalDateTime.now();
        this.solucao = false;
        this.apagado = false;
        this.usuario = usuario;
        this.topico = topico;
    }

    public void atualizarResposta(AtualizarRespostaDTO atualizarRespostaDTO){
        if(atualizarRespostaDTO.mensagem() != null){
            this.mensagem = atualizarRespostaDTO.mensagem();
        }
        if (atualizarRespostaDTO.solucao() != null){
            this.solucao = atualizarRespostaDTO.solucao();
        }
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    public void eliminarResposta(){
        this.apagado = true;
    }

}
