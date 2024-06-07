package br.com.fiap.Jannos.dto.response;

import lombok.Builder;

@Builder
public record FocoResponse(

        Long id,
        String descricao,
        EnderecoResponse endereco,
        DenunciaResponse denuncia

) {
}
