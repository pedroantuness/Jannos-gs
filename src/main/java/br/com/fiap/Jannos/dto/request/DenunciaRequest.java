package br.com.fiap.Jannos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DenunciaRequest(

        @Size(max = 255)
        @NotNull(message = "O status é obrigatório!")
        String status,

        @Size(min = 5, max = 255)
        String descricao,

        @NotNull(message = "Atribuir uma pessoa a denuncia é obrigatório!")
        AbstractRequest pessoa

) {
}
