package com.api.library.repositories;


import com.api.library.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repositório para a entidade Livro
 * Permite realizar operações de persistência para empréstimos, como busca, criação e remoção
 */
@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
}
