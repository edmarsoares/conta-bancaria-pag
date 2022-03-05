package br.com.contabancaria.util;

import java.util.Random;

public class Utils {

	public static String gerarNumeroRandomico(int tamanho) {
		Random radom = new Random();
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < tamanho; i++) {
			int numRandom = radom.nextInt(10);
			builder.append(numRandom);
		}

		System.out.println(builder.toString());
		return builder.toString();
	}
}
