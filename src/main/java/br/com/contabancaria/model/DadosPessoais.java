package br.com.contabancaria.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "t_dados_pessoais")
@NoArgsConstructor
@AllArgsConstructor
public class DadosPessoais {
	
	@Id
	@SequenceGenerator(name = "seq_dados_pessoais", sequenceName = "seq_dados_pessoais", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dados_pessoais")
	private Long id;
	
	@Column
	private String nomeCompleto;
	
	@Column
	private String idade;
	
	@Column
	private LocalDate dataNascimento;
	
	@Embedded
	private Endereco endereco;
	
	@Column(unique = true)
	private String cpf;
}
