package com.alura.desafio_forum_hub.domain.topico.repository;

import com.alura.desafio_forum_hub.domain.topico.Estado;
import com.alura.desafio_forum_hub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico,Long> {

    @SuppressWarnings("null")
    Page<Topico> findAll(Pageable pageable);

    Page<Topico> findAllByEstadoIsNot(Estado estado, Pageable pageable);

    Boolean existsByTituloAndMensagem(String titulo, String mensagem);

    Topico findByTitulo(String titulo);

}
