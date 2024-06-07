package br.com.fiap.Jannos.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RecursoRequest(

        @NotNull(message = "Valor é obrigatório!")
        Double valor,

        LocalDate data,

        @NotNull(message = "Foco é obrigatório!")
        AbstractRequest foco

) {
}
