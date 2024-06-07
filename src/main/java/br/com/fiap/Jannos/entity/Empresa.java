package br.com.fiap.Jannos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "T_EMPRESA")
public class Empresa {

    @Id
    @SequenceGenerator(name = "SQ_EMPRESA", sequenceName = "SQ_EMPRESA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EMPRESA")
    @Column(name = "ID_EMPRESA")
    private Long id;

    @Column(name = "NM_EMPRESA")
    private String nome;

    @Column(name = "DS_AREA")
    private String area;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "ENDERECO",
            referencedColumnName = "ID_ENDERECO",
            foreignKey = @ForeignKey(
                    name = "FK_EMPRESA_ENDERECO"
            )
    )
    private Endereco endereco;

}
