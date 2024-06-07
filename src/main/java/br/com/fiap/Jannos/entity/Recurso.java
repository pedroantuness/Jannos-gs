package br.com.fiap.Jannos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "T_RECURSO")
public class Recurso {

    @Id
    @SequenceGenerator(name = "SQ_RECURSO", sequenceName = "SQ_RECURSO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_RECURSO")
    @Column(name = "ID_RECURSO")
    private Long id;

    @Column(name = "VL_RECURSO")
    private Double valor;

    @Column(name = "DT_RECURSO")
    private LocalDate data;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "FOCO",
            referencedColumnName = "ID_FOCO",
            foreignKey = @ForeignKey(
                    name = "FK_RECURSO_FOCO"
            )
    )
    private Foco foco;

}
