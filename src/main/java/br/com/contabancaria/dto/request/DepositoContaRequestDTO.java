package br.com.contabancaria.dto.request;

import java.time.LocalDate;

import br.com.contabancaria.model.Banco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositoContaRequestDTO {
	
	private Banco banco;
	
	private Double valor;
	
	private String numeroConta;
	
	private String cpf;
}
