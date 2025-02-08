package com.api.library.services;

import com.api.library.dtos.EmprestimoDTO;
import com.api.library.entities.Emprestimo;
import com.api.library.entities.Livro;
import com.api.library.entities.Usuario;
import com.api.library.enums.Categoria;
import com.api.library.enums.Status;
import com.api.library.repositories.LivroRepository;
import com.api.library.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Classe de testes para a classe EmprestimoService
 */
@ExtendWith(MockitoExtension.class)
class EmprestimoServiceTest {

    @InjectMocks
    EmprestimoService emprestimoService;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    LivroRepository livroRepository;

    Emprestimo emprestimo;

    @BeforeEach
    public void setUp() {
        emprestimo = new Emprestimo(null, LocalDate.now(), LocalDate.now(), Status.EMPRESTADO, null, null);
    }

    @Test
    @DisplayName("Deve lancar exceção quando o livro ja estiver emprestado")
    void deveLancarExcecaoQuandoLivroJaEstaEmprestado() {
        Livro livro = new Livro(1, "Livro 1", "Autor 1", "ISBN 1", Categoria.DRAMA, null);
        Usuario usuario = new Usuario(1, "Nome 1", "Email 1", LocalDate.now(), "Telefone 1", null);
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setStatusEmprestimo(Status.EMPRESTADO);
        when(livroRepository.findById(emprestimo.getLivro().getId())).thenReturn(Optional.of(livro));
        when(usuarioRepository.findById(emprestimo.getUsuario().getId())).thenReturn(Optional.of(usuario));
        livro.setEmprestimos(List.of(emprestimo));
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO(emprestimo.getId(), emprestimo.getDataEmprestimo(), emprestimo.getDataDevolucao(), emprestimo.getStatusEmprestimo(), emprestimo.getUsuario().getId(), emprestimo.getLivro().getId());

        assertThrows(IllegalArgumentException.class, () -> emprestimoService.createEmprestimo(emprestimoDTO), "Livro ja possui um emprestimo ativo");
    }

}