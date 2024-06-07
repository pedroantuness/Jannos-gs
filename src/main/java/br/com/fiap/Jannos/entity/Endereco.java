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
@Table(name = "T_ENDERECO")
public class Endereco {

    @Id
    @SequenceGenerator(name = "SQ_ENDERECO", sequenceName = "SQ_ENDERECO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ENDERECO")
    @Column(name = "ID_ENDERECO")
    private Long id;

    @Column(name = "DS_LATITUDE")
    private String latitude;

    @Column(name = "DS_LONGITUDE")
    private String longitude;

    @Column(name = "CEP_ENDERECO")
    private String cep;

    @Column(name = "NR_ENDERECO")
    private Integer numero;

    @Column(name = "COMPLEMENTO_ENDERECO")
    private String complemento;

}
