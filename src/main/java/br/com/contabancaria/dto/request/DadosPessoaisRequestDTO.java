package br.com.contabancaria.dto.request;

import java.time.LocalDate;

import br.com.contabancaria.model.Banco;
import br.com.contabancaria.model.DadosPessoais;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosPessoaisRequestDTO {
	
	private String nomeCompleto;
	
	private String idade;
	
	private LocalDate dataNascimento;
	
	private String cpf;

	private EnderecoRequestDTO endereco;
	
	public DadosPessoais convertToDadosPessoais() {
		return DadosPessoais.builder()
		.dataNascimento(this.dataNascimento)
		.nomeCompleto(this.nomeCompleto)
		.idade(this.idade)
		.cpf(this.cpf)
		.endereco(endereco.convertToEndereco())
		.build();
	}
}
