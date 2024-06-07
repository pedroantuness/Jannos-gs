package br.com.fiap.Jannos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "T_ONG")
public class Ong {

    @Id
    @SequenceGenerator(name = "SQ_ONG", sequenceName = "SQ_ONG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ONG")
    @Column(name = "ID_ONG")
    private Long id;

    @Column(name = "NM_ONG")
    private String nome;

    @Column(name = "DS_AREA")
    private String area;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "ENDERECO",
            referencedColumnName = "ID_ENDERECO",
            foreignKey = @ForeignKey(
                    name = "FK_ONG_ENDERECO"
            )
    )
    private Endereco endereco;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "TB_ONG_DRONE",
            joinColumns = {
                    @JoinColumn(
                            name = "ONG",
                            referencedColumnName = "ID_ONG",
                            foreignKey = @ForeignKey(name = "FK_ONG_DRONE")
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "DRONE",
                            referencedColumnName = "ID_DRONE",
                            foreignKey = @ForeignKey(name = "FK_DRONE_ONG")
                    )
            }
    )
    private Set<Drone> drones = new LinkedHashSet<>();

}
