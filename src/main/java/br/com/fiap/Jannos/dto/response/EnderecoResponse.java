package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

@Builder
public record EnderecoResponse(

        Long id,
        String latitude,
        String longitude,
        String cep,
        Integer numero,
        String complemento

) {
}
