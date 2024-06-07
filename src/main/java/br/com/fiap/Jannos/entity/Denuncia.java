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
@Table(name = "T_DENUNCIA")
public class Denuncia {

    @Id
    @SequenceGenerator(name = "SQ_DENUNCIA", sequenceName = "SQ_DENUNCIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DENUNCIA")
    @Column(name = "ID_DENUNCIA")
    private Long id;

    @Column(name = "DS_STATUS")
    private String status;

    @Column(name = "DS_DENUNCIA")
    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "PESSOA",
            referencedColumnName = "ID_PESSOA",
            foreignKey = @ForeignKey(
                    name = "FK_DENUNCIA_PESSOA"
            )
    )
    private Pessoa pessoa;

}
