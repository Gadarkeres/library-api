package com.api.library.services;

import com.api.library.dtos.LivroDTO;
import com.api.library.entities.Livro;
import com.api.library.exceptions.NotFoundException;
import com.api.library.repositories.LivroRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * Classe responsável por fornecer serviços relacionados aos livros.
 * Esta classe inclui operações para criar, atualizar, buscar e deletar livros.
 */
@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository repository;
    private final ModelMapper mapper;
    /**
     * Busca todos os livros cadastrados no sistema
     *
     * @return Uma lista de LivroDTO contendo os dados de todos os livros
     */
    @Transactional(rollbackFor = Exception.class)
    public List<LivroDTO> findLivroList() {
        return repository.findAll().stream().map((livro -> mapper.map(livro, LivroDTO.class))).toList();
    }

    /**
     * Busca um livro específico pelo seu ID.
     *
     * @return Um LivroDTO contendo os dados do livro encontrado.
     * @throws NotFoundException Se o livro não for encontrado.
     */
    @Transactional(rollbackFor = Exception.class)
    public LivroDTO findLivroById(Integer id) {
        Livro livro = repository.findById(id).orElseThrow(() -> new NotFoundException("Livro não encontrado"));
        return mapper.map(livro, LivroDTO.class);
    }

    /**
     * Cria um novo livro no sistema.
     *
     * @return Um LivroDTO com os dados do livro recém-criado.
     */
    @Transactional(rollbackFor = Exception.class)
    public LivroDTO createLivro(LivroDTO livroDTO) {
        Livro livroEntity = repository.save(mapper.map(livroDTO, Livro.class));
        return mapper.map(livroEntity, LivroDTO.class);
    }

    /**
     * Atualiza parcialmente os dados de um livro existente.
     *
     * @return Um LivroDTO com os dados do livro atualizado.
     * @throws NotFoundException Se o livro não for encontrado.
     */
    @Transactional(rollbackFor = Exception.class)
    public LivroDTO patchLivro(@Valid LivroDTO livroDTO) {
        Livro livro = repository.findById(livroDTO.getId()).orElseThrow(() -> new NotFoundException("Livro não encontrado"));
        BeanUtils.copyProperties(livroDTO, livro);
        return mapper.map(repository.save(livro), LivroDTO.class);
    }

    /**
     * Deleta um livro pelo seu ID.
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteLivro(Integer id) {
        this.repository.deleteById(id);
    }
}
