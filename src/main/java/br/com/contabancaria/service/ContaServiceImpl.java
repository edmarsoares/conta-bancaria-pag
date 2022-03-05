package br.com.contabancaria.service;

import org.springframework.stereotype.Service;

import br.com.contabancaria.dto.request.ContaRequestDTO;
import br.com.contabancaria.model.Banco;
import br.com.contabancaria.model.Conta;
import br.com.contabancaria.repository.ContaRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ContaServiceImpl implements ContaService {

	private ContaRepository repository;
	
	@Override
	public void abrirConta(ContaRequestDTO contaDto) throws Exception {
		log.info("Abrindo conta para o usuario: {} ", contaDto.getDadosPessoais().getNomeCompleto());

		Conta conta = gerarConta(contaDto);
		
		repository.save(conta);
	}

	private Conta gerarConta(ContaRequestDTO contaDto) throws Exception {
		Conta conta = contaDto.convertToConta();
		
		boolean existeContaAssociada = repository.existeContaAssociada(conta.getDadosPessoais().getCpf(), conta.getNumeroConta());
		
		if (existeContaAssociada) {
			log.error("Ja existe uma conta associada ao usuário de nome: {} ", conta.getDadosPessoais().getNomeCompleto());
			throw new Exception("Já existe uma conta associada ao usuário informado");
		}
		Banco banco = contaDto.getBanco();
		conta.setIdBanco(banco.gerarIdentificadorBanco());
		conta.setNumeroConta(banco.gerarNumeroConta());
		conta.setAgencia(banco.gerarNumeroAgencia());
        
		return conta;
	}

	@Override
	public Conta buscar(String nome, String agencia) {
		Conta conta = repository.buscarContaPorNomeEAgencia(nome, agencia);
		return conta;
	}

	@Override
	public Conta buscar(String numeroConta) {
		return repository.findByNumeroConta(numeroConta);
	}	
}
