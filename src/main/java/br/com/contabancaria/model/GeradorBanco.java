package br.com.contabancaria.model;

public interface GeradorBanco {

	String getIdentificadorBanco();
	String gerarNumeroConta();
	String gerarNumeroAgencia();
}
