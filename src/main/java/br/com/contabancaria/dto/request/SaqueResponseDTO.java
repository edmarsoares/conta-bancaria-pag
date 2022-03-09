package br.com.contabancaria.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaqueResponseDTO {
	
	private String numeroContaFormatada;
	
	private Double saldoAtual;
	
	private Double valorDoSaque;
	
	private String mensagem;
}
