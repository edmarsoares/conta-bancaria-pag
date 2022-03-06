package br.com.contabancaria.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltroRequestDTO {
	
	private String nome;
	
	private String agencia;
}
