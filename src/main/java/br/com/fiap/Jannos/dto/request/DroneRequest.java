package br.com.fiap.Jannos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DroneRequest(

        @Size(min = 3, max = 255)
        @NotNull(message = "Nome é obrigatório!")
        String nome,

        @Size(min = 3, max = 255)
        @NotNull(message = "O tipo é obrigatório!")
        String tipo,

        @Size(min = 3, max = 255)
        @NotNull(message = "O modelo é obrigatório!")
        String modelo

) {
}
