package br.com.fiap.Jannos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRequest(

        @Size(max = 255)
        @NotNull(message = "Latitude é obrigatória!")
        String latitude,

        @Size(max = 255)
        @NotNull(message = "Longitude é obrigatória!")
        String longitude,

        @Pattern(regexp = "([0-9]{5}-?[0-9]{3} | [0-9]{8})")
        String cep,

        Integer numero,

        String complemento

) {
}
