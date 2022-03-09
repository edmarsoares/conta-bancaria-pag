package br.com.contabancaria.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "t_cheque_especial")
@NoArgsConstructor
@AllArgsConstructor
public class ChequeEspecial {

	@Id
	@SequenceGenerator(name = "seq_cheque_especial", sequenceName = "seq_cheque_especial", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cheque_especial")
	private Long id;
	
	//A cada saque este valor é decrementado
	@Column
	private Double valorDisponivel;
	
	//A cada saque este valor é incrementado e posteriormente aplicado a taxa
	@Column()
	private Double saldoAcumulado;
	
    @Enumerated(EnumType.STRING)
	private StatusChequeEspecial statusChequeEspecial;
    
    @Column
    private Double taxa;
    
}
