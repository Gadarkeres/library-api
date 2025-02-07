package com.api.library.entities;

import com.api.library.enums.Categoria;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "livros")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    private String autor;

    private String isbn;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @OneToMany(mappedBy = "livro")
    private List<Emprestimo> emprestimos;

}
