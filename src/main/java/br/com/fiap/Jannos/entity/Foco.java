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
@Table(name = "T_FOCO")
public class Foco {

    @Id
    @SequenceGenerator(name = "SQ_FOCO", sequenceName = "SQ_FOCO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FOCO")
    @Column(name = "ID_FOCO")
    private Long id;

    @Column(name = "DS_FOCO")
    private String descricao;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "ENDERECO",
            referencedColumnName = "ID_ENDERECO",
            foreignKey = @ForeignKey(
                    name = "FK_FOCO_ENDERECO"
            )
    )
    private Endereco endereco;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "DENUNCIA",
            referencedColumnName = "ID_DENUNCIA",
            foreignKey = @ForeignKey(
                    name = "FK_FOCO_DENUNCIA"
            )
    )
    private Denuncia denuncia;

}
