package com.api.library.repositories;


import com.api.library.entities.Livro;
import com.api.library.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Repositório para a entidade Livro
 * Permite realizar operações de persistência para empréstimos, como busca, criação e remoção
 */
@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
    List<Livro> findByCategoriaInAndIdNotIn(Set<Categoria> categorias, Set<Integer> ids);
}
