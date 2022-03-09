package br.com.contabancaria.service;

import br.com.contabancaria.model.Conta;
import br.com.contabancaria.model.Operacao;

public interface MovimentacaoService  {
	
	void gerarMovimentacao(double valor, Conta conta, Operacao operacao);
}
