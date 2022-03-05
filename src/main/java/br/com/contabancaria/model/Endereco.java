package br.com.contabancaria.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
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
