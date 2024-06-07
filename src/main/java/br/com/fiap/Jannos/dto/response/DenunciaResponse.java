package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

@Builder
public record DenunciaResponse(

        Long id,
        String status,
        String descricao,
        PessoaResponse pessoa

) {
}
