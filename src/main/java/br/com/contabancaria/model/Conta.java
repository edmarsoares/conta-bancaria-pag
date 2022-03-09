package br.com.contabancaria.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "t_conta")
@NoArgsConstructor
@AllArgsConstructor
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
	@OneToOne(cascade=CascadeType.PERSIST)
	private DadosPessoais dadosPessoais;
	
	@Column
	private Double saldo;
	
	@JoinColumn(name = "fk_id_cheque_especial")
	@OneToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
    private ChequeEspecial chequeEspecial;
	
    @Enumerated(EnumType.STRING)
	private TipoConta tipoConta;
	
	@Column
	private boolean chequeEspecialAtivo;
}
