package br.com.fiap.Jannos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collection;

public record OngRequest(

        @Size(min = 3, max = 255)
        @NotNull(message = "Nome é obrigatório!")
        String nome,

        @Size(min = 3, max = 255)
        @NotNull(message = "A área de atuação é obrigatória!")
        String area,

        @NotNull(message = "Endereço é obrigatório!")
        AbstractRequest endereco,

        Collection<AbstractRequest> drones

) {
}
