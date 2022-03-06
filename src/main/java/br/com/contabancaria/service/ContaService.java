package br.com.contabancaria.service;

import br.com.contabancaria.dto.request.ContaRequestDTO;
import br.com.contabancaria.dto.request.DepositoContaRequestDTO;
import br.com.contabancaria.dto.request.FiltroRequestDTO;
import br.com.contabancaria.model.Conta;

public interface ContaService  {
	
	void abrirConta(ContaRequestDTO conta) throws Exception;

	Conta buscaFiltrada(FiltroRequestDTO filter);

	Conta buscar(String numeroConta);

	void removerConta(Long id);

	void depositar(DepositoContaRequestDTO deposito) throws Exception;
}
