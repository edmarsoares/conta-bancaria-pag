package br.com.contabancaria.model;

import br.com.contabancaria.util.Utils;

public enum Banco implements GeradorBanco {

	BANCO_BRASIL("Banco do Brasil") {
		@Override
		public String getIdentificadorBanco() {
			return "001";
		}

		@Override
		public String gerarNumeroConta() {
			return Utils.gerarNumeroRandomico(5);
		}

		@Override
		public String gerarNumeroAgencia() {
			return Utils.gerarNumeroRandomico(4);
		}
	},
	BANCO_BRADESCO("Bradesco") {
		@Override
		public String getIdentificadorBanco() {
			return "237";
		}
		
		@Override
		public String gerarNumeroConta() {
			return Utils.gerarNumeroRandomico(5);
		}
		
		@Override
		public String gerarNumeroAgencia() {
			return Utils.gerarNumeroRandomico(4);
		}
	},
	BANCO_SANTANDER("Santander") {
		@Override
		public String getIdentificadorBanco() {
			return "033";
		}
		
		@Override
		public String gerarNumeroConta() {
			return Utils.gerarNumeroRandomico(5);
		}
		
		@Override
		public String gerarNumeroAgencia() {
			return Utils.gerarNumeroRandomico(4);
		}
	},
	BANCO_ITAU("Itaú") {
		@Override
		public String getIdentificadorBanco() {
			return "341";
		}
		
		@Override
		public String gerarNumeroConta() {
			return Utils.gerarNumeroRandomico(5);
		}
		
		@Override
		public String gerarNumeroAgencia() {
			return Utils.gerarNumeroRandomico(4);
		}
	};

	String name;

	Banco(String name) {
		this.name = name;
	}
}
