package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

@Builder
public record EmpresaResponse(

        Long id,
        String nome,
        String area,
        EnderecoResponse endereco

) {
}
