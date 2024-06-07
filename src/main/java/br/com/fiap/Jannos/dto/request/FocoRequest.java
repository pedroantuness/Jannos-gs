package br.com.fiap.Jannos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FocoRequest(

        @Size(min = 3, max = 255)
        String descricao,

        @NotNull(message = "Endereço é obrigatório!")
        AbstractRequest endereco,

        @NotNull(message = "Denuncia é obrigatória!")
        AbstractRequest denuncia

) {
}
