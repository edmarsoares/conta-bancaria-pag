package br.com.contabancaria.dto.response;

import br.com.contabancaria.model.Conta;
import br.com.contabancaria.model.StatusChequeEspecial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
				.statusChequeEspecial(conta.getChequeEspecial() != null ? conta.getChequeEspecial().getStatusChequeEspecial() : null)
				.build();
	}
}
