package com.api.library.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Entidade de representação da tabela "Usuarios"
 */
@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String email;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    private String telefone;

    @OneToMany(mappedBy = "usuario")
    private List<Emprestimo> emprestimos;

}
