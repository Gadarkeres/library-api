package com.api.library.services;

import com.api.library.dtos.LivroDTO;
import com.api.library.entities.Livro;
import com.api.library.exceptions.NotFoundException;
import com.api.library.repositories.LivroRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivroService {
    private final LivroRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public LivroService(LivroRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<LivroDTO> findLivroList() {
        return repository.findAll().stream().map((livro -> mapper.map(livro, LivroDTO.class))).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public LivroDTO findLivroById(Integer id) {
        Livro livro = repository.findById(id).orElseThrow(() -> new NotFoundException("Livro não encontrado"));
        return mapper.map(livro, LivroDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public LivroDTO createLivro(LivroDTO livroDTO) {
        Livro livroEntity = repository.save(mapper.map(livroDTO, Livro.class));
        return mapper.map(livroEntity, LivroDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public LivroDTO patchLivro(@Valid LivroDTO livroDTO) {
        Livro livro = repository.findById(livroDTO.getId()).orElseThrow(() -> new NotFoundException("Livro não encontrado"));
        BeanUtils.copyProperties(livroDTO, livro);
        return mapper.map(repository.save(livro), LivroDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteLivro(Integer id) {
        this.repository.deleteById(id);
    }
}
