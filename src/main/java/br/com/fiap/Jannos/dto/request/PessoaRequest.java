package br.com.fiap.Jannos.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record PessoaRequest(

        @Size(min = 3, max = 255)
        @NotNull(message = "Nome é obrigatório!")
        String nome,

        @CPF(message = "CPF inválido!")
        String cpf,

        @Email(message = "Email inválido!")
        String email,

        @NotNull(message = "Endereço é obrigatório!")
        AbstractRequest endereco

) {
}
