package br.com.contabancaria.dto.request;

import br.com.contabancaria.model.Banco;
import br.com.contabancaria.model.Conta;
import br.com.contabancaria.model.TipoConta;
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
	
	private TipoConta tipoConta;
	
	public Conta convertToConta() {
		return Conta.builder()
		.saldo(this.saldoInicial)
		.tipoConta(this.tipoConta)
		.dadosPessoais(dadosPessoais.convertToDadosPessoais())
		.build();
	}
}
