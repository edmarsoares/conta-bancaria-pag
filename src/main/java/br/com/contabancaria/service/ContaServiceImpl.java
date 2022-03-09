package br.com.contabancaria.service;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.contabancaria.dto.request.ContaRequestDTO;
import br.com.contabancaria.dto.request.DepositoContaRequestDTO;
import br.com.contabancaria.dto.request.FiltroRequestDTO;
import br.com.contabancaria.dto.request.SaqueContaRequestDTO;
import br.com.contabancaria.dto.request.SaqueResponseDTO;
import br.com.contabancaria.dto.response.ContaResponseDTO;
import br.com.contabancaria.model.Banco;
import br.com.contabancaria.model.ChequeEspecial;
import br.com.contabancaria.model.Conta;
import br.com.contabancaria.model.Operacao;
import br.com.contabancaria.model.StatusChequeEspecial;
import br.com.contabancaria.model.TipoConta;
import br.com.contabancaria.repository.ContaRepository;
import br.com.contabancaria.util.Utils;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ContaServiceImpl implements ContaService {

	private static final Double VALOR_DEFAULT = 500.0;

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
		conta.setIdBanco(banco.getIdentificadorBanco());
		conta.setNumeroConta(banco.gerarNumeroConta());
		conta.setAgencia(banco.gerarNumeroAgencia());
		conta.setChequeEspecialAtivo(Boolean.FALSE);
		
		gerarChequeEspecial(contaDto, conta);
		
		return conta;
	}

	private void gerarChequeEspecial(ContaRequestDTO contaDto, Conta conta) {
		if (contaDto.getTipoConta().equals(TipoConta.CORRENTE)) {
			// cria um cheque especial mas não libera pq a conta possui saldo
			conta.setChequeEspecial(ChequeEspecial
					.builder()
					.valorDisponivel(VALOR_DEFAULT)
					.saldoAcumulado(0.0)
					.taxa(0.02)
					.statusChequeEspecial(StatusChequeEspecial.NAO_LIBERADO)
					.build()
					);
		}
	}

	@Transactional
	@Override
	public Conta buscaFiltrada(FiltroRequestDTO filter) {
		Conta conta = repository.buscarContaPorNomeEAgencia(filter.getNome(), filter.getAgencia(), filter.isChequeEspecialAtivo());
		return conta;
	}

	@Transactional
	@Override
	public ContaResponseDTO buscar(String numeroConta) throws ParseException {
		Optional<Conta> contaOpt = repository.findByNumeroConta(numeroConta);
		
		if (contaOpt.isPresent()) {
			Conta conta = contaOpt.get();
			
			ContaResponseDTO contaResponseDto = 
					ContaResponseDTO.builder()
					.saldoFormatado(Utils.formatarMoeda(conta.getSaldo()))
					.agenciaFormatada(Utils.formatarAgencia(conta.getAgencia(), conta.getNumeroConta()))
					.nome(conta.getDadosPessoais().getNomeCompleto())
					.statusChequeEspecial(conta.getChequeEspecial().getStatusChequeEspecial())
					.valorChequeEspecialFormatado(Utils.formatarMoeda(conta.getChequeEspecial().getValorDisponivel()))
					.build();
			
			return contaResponseDto;
		}
		return null;
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
		
		double valorParaDepositoEmConta = deposito.getValor();
		
		if (conta.isChequeEspecialAtivo()) {
			Double saldoAcumulado = conta.getChequeEspecial().getSaldoAcumulado();
			
			if (deposito.getValor() >= saldoAcumulado) {
				valorParaDepositoEmConta = deposito.getValor() - saldoAcumulado;
				
				conta.getChequeEspecial().setValorDisponivel(VALOR_DEFAULT);
				conta.getChequeEspecial().setStatusChequeEspecial(StatusChequeEspecial.NAO_LIBERADO);
				conta.getChequeEspecial().setSaldoAcumulado(0.0);
				conta.setChequeEspecialAtivo(Boolean.FALSE);
				
			} else {
				conta.getChequeEspecial().setSaldoAcumulado(saldoAcumulado - deposito.getValor());
				valorParaDepositoEmConta = 0.0;
			}
		}
		
		Double saldo = conta.getSaldo();
		conta.setSaldo(saldo += valorParaDepositoEmConta);
		
		Conta contaSalva = repository.save(conta);
		
		movimentacaoService.gerarMovimentacao(valorParaDepositoEmConta, contaSalva, Operacao.CREDITO);		
	}

	private Optional<Conta> buscarConta(DepositoContaRequestDTO deposito) throws Exception {
		Optional<Conta> contaOpt = buscarConta(deposito.getCpf(), deposito.getBanco(), deposito.getNumeroConta());
		return contaOpt;
	}

	private Optional<Conta> buscarConta(String cpf, Banco banco, String numeroConta) throws Exception {
		Optional<Conta> contaOpt = repository.buscarContaPorCpfIdBancoENumeroConta(
				cpf, 
				banco.getIdentificadorBanco(), 
				numeroConta);
	
		if (!contaOpt.isPresent()) {
			log.error("Não existe uma conta associada ao cpf: {} ", cpf);
			throw new Exception("Não existe uma conta associada ao cpf");
		}
		return contaOpt;
	}

	@Transactional
	@Override
	public SaqueResponseDTO sacar(SaqueContaRequestDTO saque) throws Exception {
		Conta conta = this.buscarConta(saque.getCpf(), saque.getBanco(), saque.getNumeroConta()).get();
		
		validarSePossuiSaldo(saque, conta);

		if (conta.isChequeEspecialAtivo()) {
			conta.getChequeEspecial().setSaldoAcumulado(validarValorChequeEspecial(conta.getChequeEspecial(), saque));
		}
		repository.save(conta);
		movimentacaoService.gerarMovimentacao(saque.getValor(), conta, Operacao.DEBITO);		

		return SaqueResponseDTO.builder()
		.numeroContaFormatada(Utils.formatarAgencia(conta.getAgencia(), conta.getNumeroConta()))
		.saldoAtual(conta.getSaldo())
		.valorDoSaque(saque.getValor())
		.mensagem(conta.isChequeEspecialAtivo() ? "Você está utilizando o saldo do cheque especial": "Saque realizado com sucesso!")
		.build();
	}

	private Double validarValorChequeEspecial(ChequeEspecial chequeEspecial, SaqueContaRequestDTO saque) throws Exception {
		Double valorSaque = saque.getValor();
		Double saldoAcumulado = chequeEspecial.getSaldoAcumulado();
		Double valorLiberado = chequeEspecial.getValorDisponivel();
		
		if (chequeEspecial.getValorDisponivel() >= valorSaque) {
			chequeEspecial.setValorDisponivel(valorLiberado -= valorSaque);
			
			return saldoAcumulado += valorSaque;
		} else {
			log.error("Saldo insuficiente do cheque especial para saque de valor : {}", saque.getValor());
			throw new Exception("Saldo insuficiente do cheque especial para saque");				
		}
	}

	private void validarSePossuiSaldo(SaqueContaRequestDTO saque, Conta conta) throws Exception {
		Double saldo = conta.getSaldo();
		if (saldo > 0 &&  saldo < saque.getValor()) {
			log.error("O valor de saque é maior do que o saldo atual : {}", saque.getValor());
			throw new Exception("O valor de saque é maior do que o saldo atual");	
			
		} else if (saldo > 0 && saldo >= saque.getValor()) {
			conta.setSaldo(saldo -= saque.getValor());
		} else {
			if (conta.getTipoConta().equals(TipoConta.CORRENTE)) {
				log.info("Ativando o cheque especial");
				ativarChequeEspecial(conta);
			} else {
				log.error("Saldo insuficiente para saque de valor : {}", saque.getValor());
				throw new Exception("Saldo insuficiente para saque");				
			}
		}
	}

	private void ativarChequeEspecial(Conta conta) {
		conta.getChequeEspecial().setStatusChequeEspecial(StatusChequeEspecial.LIBERADO);
		conta.setChequeEspecialAtivo(Boolean.TRUE);
	}	
}
