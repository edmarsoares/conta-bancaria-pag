package br.com.contabancaria.service;

import java.text.ParseException;

import br.com.contabancaria.dto.request.ContaRequestDTO;
import br.com.contabancaria.dto.request.DepositoContaRequestDTO;
import br.com.contabancaria.dto.request.FiltroRequestDTO;
import br.com.contabancaria.dto.request.SaqueContaRequestDTO;
import br.com.contabancaria.dto.request.SaqueResponseDTO;
import br.com.contabancaria.dto.response.ContaResponseDTO;
import br.com.contabancaria.model.Conta;

public interface ContaService  {
	
	void abrirConta(ContaRequestDTO conta) throws Exception;

	Conta buscaFiltrada(FiltroRequestDTO filter);

	ContaResponseDTO buscar(String numeroConta) throws ParseException;

	void removerConta(Long id);

	void depositar(DepositoContaRequestDTO deposito) throws Exception;

	SaqueResponseDTO sacar(SaqueContaRequestDTO saque) throws Exception;
}
