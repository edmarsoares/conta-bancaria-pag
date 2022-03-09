package br.com.contabancaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.contabancaria.dto.request.DepositoContaRequestDTO;
import br.com.contabancaria.model.Conta;
import br.com.contabancaria.model.Movimentacao;
import br.com.contabancaria.model.Operacao;
import br.com.contabancaria.repository.MovimentacaoRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MovimentacaoServiceImpl implements MovimentacaoService {

	@Autowired
	private MovimentacaoRepository repository;

	
	@Transactional
	@Override
	public void gerarMovimentacao(double valor, Conta conta, Operacao operacao) {
		log.info("Gerando movimentacao para o cpf: {}", conta.getDadosPessoais().getCpf());
		
		Movimentacao movimentacao = Movimentacao.builder()
		.agencia(conta.getAgencia())
		.dadosPessoais(conta.getDadosPessoais())
		.valor(valor)
		.idConta(conta)
		.operacao(operacao)
		.numeroConta(conta.getNumeroConta())
		.statusChequeEspecial(conta.getChequeEspecial() != null ? conta.getChequeEspecial().getStatusChequeEspecial() : null)
		.nomeCliente(conta.getDadosPessoais().getNomeCompleto())
		.build();
		
		repository.save(movimentacao);
	}
	
	
}
