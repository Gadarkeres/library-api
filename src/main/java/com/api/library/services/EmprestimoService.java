package com.api.library.services;

import com.api.library.dtos.EmprestimoDTO;
import com.api.library.entities.Emprestimo;
import com.api.library.entities.Livro;
import com.api.library.entities.Usuario;
import com.api.library.enums.Status;
import com.api.library.exceptions.NotFoundException;
import com.api.library.repositories.EmprestimoRepository;
import com.api.library.repositories.LivroRepository;
import com.api.library.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmprestimoService {
    private final EmprestimoRepository repository;
    private final ModelMapper mapper;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public EmprestimoService(EmprestimoRepository repository, ModelMapper mapper, LivroRepository livroRepository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public EmprestimoDTO createEmprestimo(@Valid EmprestimoDTO emprestimoDTO) {
        Livro livro = livroRepository.findById(emprestimoDTO.getLivroId()).orElseThrow(() -> new NotFoundException("Livro não encontrado"));
        Usuario usuario = usuarioRepository.findById(emprestimoDTO.getUsuarioId()).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if(!checkIfEmprestimoIsValid(livro)) {
            throw new IllegalArgumentException("Este livro ja possui um emprestimo ativo: " + livro.getId());
        }

        Emprestimo emprestimo = new Emprestimo(null, emprestimoDTO.getDataEmprestimo(), emprestimoDTO.getDataDevolucao(), emprestimoDTO.getStatusEmprestimo(), usuario, livro);
        return mapper.map(repository.save(emprestimo), EmprestimoDTO.class);
    }

    public Boolean checkIfEmprestimoIsValid(Livro livro) {
        return livro.getEmprestimos().stream().allMatch(emprestimo -> emprestimo.getStatusEmprestimo().equals(Status.DEVOLVIDO));
    }

    public List<EmprestimoDTO> findEmprestimoList() {
        return repository.findAll().stream().map((emprestimo -> mapper.map(emprestimo, EmprestimoDTO.class))).toList();
    }
}
