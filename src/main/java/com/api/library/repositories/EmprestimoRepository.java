package com.api.library.repositories;

import com.api.library.entities.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Emprestimo
 * Permite realizar operações de persistência para empréstimos, como busca, criação e remoção
 */
@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {
}
