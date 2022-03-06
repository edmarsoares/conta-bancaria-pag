package br.com.contabancaria.service;

import br.com.contabancaria.dto.request.DepositoContaRequestDTO;
import br.com.contabancaria.model.Conta;
import br.com.contabancaria.model.Operacao;

public interface MovimentacaoService  {
	
	void gerarMovimentacao(DepositoContaRequestDTO deposito, Conta conta, Operacao operacao);
}
