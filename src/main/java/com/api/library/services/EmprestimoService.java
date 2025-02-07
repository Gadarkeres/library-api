package com.api.library.services;

import com.api.library.dtos.EmprestimoDTO;
import com.api.library.dtos.PatchEmprestimoDTO;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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


    /**
     * Cria um novo emprestimo para o usuario.
     * Esse metodo valida se o livro ja possui um emprestimo ativo, caso tenha ele lança um erro
     * Se não houver emprestimos ativos, cria o emprestimo
     *
     * @return Um objeto EmprestimoDTO com os dados do empréstimo recém-criado
     * @throws NotFoundException        Se o livro ou o usuário informado não forem encontrados
     * @throws IllegalArgumentException Se o livro já tiver um empréstimo ativo
     */
    @Transactional(rollbackFor = Exception.class)
    public EmprestimoDTO createEmprestimo(@Valid EmprestimoDTO emprestimoDTO) {
        Livro livro = this.findLivroById(emprestimoDTO.getLivroId());
        Usuario usuario = this.findUsuarioById(emprestimoDTO.getUsuarioId());

        if (!checkIfEmprestimoIsValid(livro)) {
            throw new IllegalArgumentException("Este livro ja possui um emprestimo ativo: " + livro.getId());
        }

        Emprestimo emprestimo = new Emprestimo(null, LocalDate.now(), emprestimoDTO.getDataDevolucao(), Status.EMPRESTADO, usuario, livro);
        return mapper.map(repository.save(emprestimo), EmprestimoDTO.class);
    }

    /**
     * Verifica se um livro pode ser emprestado
     * O livro só será válido para empréstimo se todos os empréstimos associados a ele estiverem com o status "DEVOLVIDO"
     *
     * @return true se o livro estiver disponível para empréstimo, caso contrário false
     */
    public Boolean checkIfEmprestimoIsValid(Livro livro) {
        return livro.getEmprestimos().stream().allMatch(emprestimo -> emprestimo.getStatusEmprestimo().equals(Status.DEVOLVIDO));
    }

    /**
     * Retorna a lista de todos os empréstimos registrados
     * Os empréstimos são convertidos para o formato DTO para envio ao cliente
     *
     * @return Uma lista de EmprestimoDTO contendo os dados de todos os empréstimos
     */
    @Transactional(rollbackFor = Exception.class)
    public List<EmprestimoDTO> findEmprestimoList() {
        return repository.findAll().stream().map((emprestimo -> mapper.map(emprestimo, EmprestimoDTO.class))).toList();
    }

    /**
     * Atualiza parcialmente os dados de um empréstimo existente
     *
     * @return Um EmprestimoDTO com os dados do empréstimo atualizado
     */
    @Transactional(rollbackFor = Exception.class)
    public EmprestimoDTO patchEmprestimo(PatchEmprestimoDTO patchEmprestimoDTO) {
        Emprestimo emprestimo = repository.findById(patchEmprestimoDTO.getId()).orElseThrow(() -> new NotFoundException("Emprestimo não encontrado"));

        if (patchEmprestimoDTO.getStatusEmprestimo() != null) {
            emprestimo.setStatusEmprestimo(patchEmprestimoDTO.getStatusEmprestimo());
        }

        if (patchEmprestimoDTO.getDataDevolucao() != null) {
            emprestimo.setDataDevolucao(patchEmprestimoDTO.getDataDevolucao());
        }
        return mapper.map(repository.save(emprestimo), EmprestimoDTO.class);
    }

    public Livro findLivroById(Integer id) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new NotFoundException("Livro não encontrado"));
        return livro;
    }

    public Usuario findUsuarioById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return usuario;
    }
}
