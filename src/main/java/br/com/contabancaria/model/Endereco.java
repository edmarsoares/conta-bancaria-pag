package br.com.contabancaria.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
	
	@Column
	private String rua;
	
	@Column
	private String cep;
	
	@Column
	private String estado;
	
	@Column
	private String cidade;
}
