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
@Table(name = "T_DRONE")
public class Drone {

    @Id
    @SequenceGenerator(name = "SQ_DRONE", sequenceName = "SQ_DRONE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DRONE")
    @Column(name = "ID_DRONE")
    private Long id;

    @Column(name = "NM_DRONE")
    private String nome;

    @Column(name = "TP_DRONE")
    private String tipo;

    @Column(name = "DS_MODELO")
    private String modelo;

}
