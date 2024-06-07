package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

@Builder
public record PessoaResponse(

        Long id,
        String nome,
        String cpf,
        String email,
        EnderecoResponse endereco

) {
}
