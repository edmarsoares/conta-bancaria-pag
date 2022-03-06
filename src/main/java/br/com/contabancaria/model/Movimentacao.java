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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "t_movimentacao")
@NoArgsConstructor
@AllArgsConstructor
public class Movimentacao {

	@Id
	@SequenceGenerator(name = "seq_conta", sequenceName = "seq_conta", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conta")
	private Long id;
	
	@Column
	private String nomeCliente;
	
	@Column
	private String agencia;
	
	@Column
	private String numeroConta;
	
	@JoinColumn(name = "fk_id_conta")
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Conta idConta;
	
	@JoinColumn(name = "fk_id_dados_pessoais")
	@ManyToOne(cascade=CascadeType.MERGE)
	private DadosPessoais dadosPessoais;
	
	@Column
	private Double valor;
	
	@Enumerated(EnumType.STRING)
	private Operacao operacao;
	
    @Enumerated(EnumType.STRING)
	private StatusChequeEspecial statusChequeEspecial;
}
