package com.api.library.services;

import com.api.library.dtos.EmprestimoDTO;
import com.api.library.dtos.LivroDTO;
import com.api.library.dtos.PatchEmprestimoDTO;
import com.api.library.entities.Emprestimo;
import com.api.library.entities.Livro;
import com.api.library.entities.Usuario;
import com.api.library.enums.Categoria;
import com.api.library.enums.Status;
import com.api.library.repositories.EmprestimoRepository;
import com.api.library.repositories.LivroRepository;
import com.api.library.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Mock
    EmprestimoRepository emprestimoRepository;

    @Mock
    ModelMapper mapper;

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

    @Test
    @DisplayName("Deve criar um emprestimo com sucesso e vincular a um livro e usuario")
    void deveCriarEmprestimoComSucessoEVincularLivroEUsuario() {
        Livro livro = new Livro(1, "Livro 1", "Autor 1", "ISBN 1", Categoria.DRAMA, null);
        Usuario usuario = new Usuario(1, "Nome 1", "Email 1", LocalDate.now(), "Telefone 1", null);

        Emprestimo emprestimo = new Emprestimo(1, LocalDate.now(), LocalDate.now(), Status.EMPRESTADO, usuario, livro);

        when(livroRepository.findById(emprestimo.getLivro().getId())).thenReturn(Optional.of(livro));
        when(usuarioRepository.findById(emprestimo.getUsuario().getId())).thenReturn(Optional.of(usuario));
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);


        EmprestimoDTO emprestimoDTO = new EmprestimoDTO(
                emprestimo.getId(),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataDevolucao(),
                emprestimo.getStatusEmprestimo(),
                emprestimo.getUsuario().getId(),
                emprestimo.getLivro().getId()
        );
        when(mapper.map(emprestimo, EmprestimoDTO.class)).thenReturn(emprestimoDTO);
        EmprestimoDTO resultado = emprestimoService.createEmprestimo(emprestimoDTO);

        assertNotNull(resultado, "O emprestimo deve ser criado com sucesso.");
        assertEquals(emprestimoDTO.getUsuarioId(), resultado.getUsuarioId(), "O id do usuario deve ser vinculado ao emprestimo.");
        assertEquals(emprestimoDTO.getLivroId(), resultado.getLivroId(), "O id do livro deve ser vinculado ao emprestimo.");
        assertEquals(emprestimoDTO.getStatusEmprestimo(), resultado.getStatusEmprestimo(), "O status do emprestimo deve ser EMPRESTADO.");
    }

    @Test
    @DisplayName("Deve recomendar a um usuario um livro da categoria que leu menos o que ja leu")
    void deveRecomendarLivro() {
        Livro livro1 = new Livro(1, "Livro 1", "Autor 1", "ISBN 1", Categoria.DRAMA, null);
        Livro livro2 = new Livro(2, "Livro 2", "Autor 2", "ISBN 2", Categoria.DRAMA, null);
        Livro livro3 = new Livro(3, "Livro 3", "Autor 3", "ISBN 3", Categoria.DRAMA, null);
        Usuario usuario = new Usuario(1, "Nome 1", "Email 1", LocalDate.now(), "Telefone 1", new ArrayList<>());

        Emprestimo emprestimo1 = new Emprestimo(1, LocalDate.now(), LocalDate.now(), Status.DEVOLVIDO, usuario, livro1);
        Emprestimo emprestimo2 = new Emprestimo(2, LocalDate.now(), LocalDate.now(), Status.DEVOLVIDO, usuario, livro2);
        usuario.getEmprestimos().addAll(List.of(emprestimo1, emprestimo2));

        List<Livro> livrosRecomendados = List.of(livro3);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(livroRepository.findByCategoriaInAndIdNotIn(
                Set.of(Categoria.DRAMA),
                Set.of(1, 2)
        )).thenReturn(livrosRecomendados);


        when(mapper.map(livro3, LivroDTO.class)).thenReturn(new LivroDTO(3, "Livro 3", "Autor 3", "ISBN 3", Categoria.FICCAO));

        List<LivroDTO> livrosRecomendadosDTO = emprestimoService.recomendarLivros(1);

        assertNotNull(livrosRecomendadosDTO, "Livros recomendados devem ser retornados.");
        assertEquals(1, livrosRecomendadosDTO.size(), "Um livro deve ser recomendado.");
        assertEquals("Livro 3", livrosRecomendadosDTO.get(0).getTitulo(), "O nome do livro recomendado deve ser 'Livro 3'.");
    }

    @Test
    @DisplayName("Deve atualizar o status de um empréstimo com sucesso")
    void deveAtualizarStatusDoEmprestimo() {
        Livro livro = new Livro(1, "Livro 1", "Autor 1", "ISBN 1", Categoria.DRAMA, null);
        Usuario usuario = new Usuario(1, "Nome 1", "Email 1", LocalDate.now(), "Telefone 1", null);
        Emprestimo emprestimo = new Emprestimo(1, LocalDate.now(), LocalDate.now(), Status.EMPRESTADO, usuario, livro);

        PatchEmprestimoDTO patchDTO = new PatchEmprestimoDTO();
        patchDTO.setId(1);
        patchDTO.setStatusEmprestimo(Status.DEVOLVIDO);

        when(emprestimoRepository.findById(1)).thenReturn(Optional.of(emprestimo));
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);
        when(mapper.map(emprestimo, EmprestimoDTO.class)).thenReturn(new EmprestimoDTO(1, LocalDate.now(), LocalDate.now(), Status.DEVOLVIDO, 1, 1));


        EmprestimoDTO resultado = emprestimoService.patchEmprestimo(patchDTO);


        assertNotNull(resultado);
        assertEquals(Status.DEVOLVIDO, resultado.getStatusEmprestimo());
        verify(emprestimoRepository, times(1)).save(emprestimo);
    }

    @Test
    @DisplayName("Deve atualizar a data de devolução de um empréstimo com sucesso")
    void deveAtualizarDataDevolucaoDoEmprestimo() {
        Livro livro = new Livro(1, "Livro 1", "Autor 1", "ISBN 1", Categoria.DRAMA, null);
        Usuario usuario = new Usuario(1, "Nome 1", "Email 1", LocalDate.now(), "Telefone 1", null);
        Emprestimo emprestimo = new Emprestimo(1, LocalDate.now(), LocalDate.now(), Status.EMPRESTADO, usuario, livro);

        LocalDate novaDataDevolucao = LocalDate.now().plusDays(7);
        PatchEmprestimoDTO patchDTO = new PatchEmprestimoDTO();
        patchDTO.setId(1);
        patchDTO.setDataDevolucao(novaDataDevolucao);

        when(emprestimoRepository.findById(1)).thenReturn(Optional.of(emprestimo));
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);
        when(mapper.map(emprestimo, EmprestimoDTO.class)).thenReturn(new EmprestimoDTO(1, LocalDate.now(), novaDataDevolucao, Status.EMPRESTADO, 1, 1));

        EmprestimoDTO resultado = emprestimoService.patchEmprestimo(patchDTO);

        assertNotNull(resultado);
        assertEquals(novaDataDevolucao, resultado.getDataDevolucao());
        verify(emprestimoRepository, times(1)).save(emprestimo);
    }

}