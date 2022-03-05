package br.com.contabancaria.service;

import br.com.contabancaria.dto.request.ContaRequestDTO;
import br.com.contabancaria.model.Conta;

public interface ContaService  {
	
	void abrirConta(ContaRequestDTO conta) throws Exception;

	Conta buscar(String nome, String agencia);

	Conta buscar(String numeroConta);
}
