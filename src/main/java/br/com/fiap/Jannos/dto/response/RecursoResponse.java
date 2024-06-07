package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RecursoResponse(

        Long id,
        Double valor,
        LocalDate data,
        FocoResponse foco

) {
}
