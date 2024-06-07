package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record OngResponse(

        Long id,
        String nome,
        String area,
        EnderecoResponse endereco,
        List<DroneResponse> drones

) {
}
