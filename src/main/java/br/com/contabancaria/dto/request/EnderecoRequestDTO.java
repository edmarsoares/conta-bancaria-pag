package br.com.contabancaria.dto.request;

import br.com.contabancaria.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequestDTO {

	private String rua;

	private String cep;

	private String estado;

	private String cidade;

	public Endereco convertToEndereco() {
		return Endereco.builder()
		.cep(this.cep)
		.rua(this.rua)
		.estado(this.estado)
		.cidade(this.cidade)
		.build();
	}
}
