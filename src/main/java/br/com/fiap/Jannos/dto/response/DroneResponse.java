package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

@Builder
public record DroneResponse(

        Long id,
        String nome,
        String tipo,
        String modelo

) {
}
