package br.com.contabancaria.dto.response;

import br.com.contabancaria.model.Conta;
import br.com.contabancaria.model.StatusChequeEspecial;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContaResponseDTO {
	
	private String nome;
	
	private String agenciaFormatada;
	
	private String valorChequeEspecialFormatado;
	
	private String saldoFormatado;
	
	private StatusChequeEspecial statusChequeEspecial;
	
	public static ContaResponseDTO convertToDto(Conta conta) {
		
		return ContaResponseDTO.builder().
				nome(conta.getDadosPessoais().getNomeCompleto())
				.agenciaFormatada(conta.getAgencia() + conta.getNumeroConta())
				//.valorChequeEspecialFormatado( conta.getSaldo())
				.saldoFormatado(String.valueOf(conta.getSaldo()))
				.statusChequeEspecial(conta.getStatusChequeEspecial())
				.build();
	}
}
