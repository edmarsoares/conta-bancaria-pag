package br.com.contabancaria.dto.request;

import br.com.contabancaria.model.Banco;
import br.com.contabancaria.model.Conta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaRequestDTO {
	
	private Banco banco;
	
	private Double saldoInicial;
	
	private DadosPessoaisRequestDTO dadosPessoais;
	
	public Conta convertToConta() {
		return Conta.builder()
		.saldo(this.saldoInicial)
		.dadosPessoais(dadosPessoais.convertToDadosPessoais())
		.build();
	}
}
