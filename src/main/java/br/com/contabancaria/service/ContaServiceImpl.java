package br.com.contabancaria.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.contabancaria.dto.request.ContaRequestDTO;
import br.com.contabancaria.dto.request.DepositoContaRequestDTO;
import br.com.contabancaria.dto.request.FiltroRequestDTO;
import br.com.contabancaria.model.Banco;
import br.com.contabancaria.model.Conta;
import br.com.contabancaria.model.Operacao;
import br.com.contabancaria.model.StatusChequeEspecial;
import br.com.contabancaria.repository.ContaRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ContaServiceImpl implements ContaService {

	@Autowired
	private ContaRepository repository;
	
	@Autowired
	private MovimentacaoService movimentacaoService;
	
	@Override
	@Transactional
	public void abrirConta(ContaRequestDTO contaDto) throws Exception {
		log.info("Abrindo conta para o usuario: {} ", contaDto.getDadosPessoais().getNomeCompleto());

		Conta conta = gerarConta(contaDto);
		
		repository.save(conta);
	}

	@Transactional
	private Conta gerarConta(ContaRequestDTO contaDto) throws Exception {
		Conta conta = contaDto.convertToConta();
		Banco banco = contaDto.getBanco();
		
		boolean existeContaAssociada = repository.existeContaAssociada(conta.getDadosPessoais().getCpf(), banco.getIdentificadorBanco());
		
		if (existeContaAssociada) {
			log.error("Ja existe uma conta associada ao usuário de nome: {} ", conta.getDadosPessoais().getNomeCompleto());
			throw new Exception("Já existe uma conta associada ao usuário informado");
		}
		conta.setStatusChequeEspecial(StatusChequeEspecial.NAO_LIBERADO);
		conta.setIdBanco(banco.getIdentificadorBanco());
		conta.setNumeroConta(banco.gerarNumeroConta());
		conta.setAgencia(banco.gerarNumeroAgencia());
        
		return conta;
	}

	@Transactional
	@Override
	public Conta buscaFiltrada(FiltroRequestDTO filter) {
		Conta conta = repository.buscarContaPorNomeEAgencia(filter.getNome(), filter.getAgencia());
		return conta;
	}

	@Transactional
	@Override
	public Conta buscar(String numeroConta) {
		return repository.findByNumeroConta(numeroConta);
	}

	@Transactional
	@Override
	public void removerConta(Long id) {
		Optional<Conta> contaOpt = repository.findById(id);		
		
		if (contaOpt.isPresent()) {
			repository.delete(contaOpt.get());
		}
	}

	@Transactional
	@Override
	public void depositar(DepositoContaRequestDTO deposito) throws Exception {
		Optional<Conta> contaOpt = buscarConta(deposito);
		Conta conta = contaOpt.get();
		
		Double saldo = conta.getSaldo();
		conta.setSaldo(saldo += deposito.getValor());
		
		Conta contaSalva = repository.save(conta);
		
		movimentacaoService.gerarMovimentacao(deposito, contaSalva, Operacao.CREDITO);		
	}

	private Optional<Conta> buscarConta(DepositoContaRequestDTO deposito) throws Exception {
		Optional<Conta> contaOpt = repository.buscarContaPorCpfIdBancoENumeroConta(
				deposito.getCpf(), 
				deposito.getBanco().getIdentificadorBanco(), 
				deposito.getNumeroConta());
	
		if (!contaOpt.isPresent()) {
			log.error("Não existe uma conta associada ao cpf: {} ", deposito.getCpf());
			throw new Exception("Não existe uma conta associada ao cpf");
		}
		return contaOpt;
	}	
}
