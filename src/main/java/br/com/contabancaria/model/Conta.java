package br.com.contabancaria.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "t_conta")
public class Conta {

	@Id
	@SequenceGenerator(name = "seq_conta", sequenceName = "seq_conta", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conta")
	private Long id;
	
	@Column
	private String idBanco;
	
	@Column
	private String agencia;
	
	@Column
	private String numeroConta;
	
	@JoinColumn(name = "fk_id_dados_pessoais")
	private DadosPessoais dadosPessoais;
	
	@Column
	private Double saldo;
	
    @Enumerated(EnumType.STRING)
	private StatusChequeEspecial statusChequeEspecial;
	
	@Column
	private boolean chequeEspecialLiberado;
}
