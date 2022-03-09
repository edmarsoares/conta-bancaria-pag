package br.com.contabancaria.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Random;

import javax.swing.text.MaskFormatter;

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

	public static String formatarMoeda(Double saldo) {
		if (saldo != null) {
			return NumberFormat.getCurrencyInstance().format(saldo);			
		}
		return null;
	}

	public static String formatarAgencia(String agencia, String numeroConta) throws ParseException {
		
		String numeroformatado = agencia.concat(numeroConta);
		String mask= "####/####-#";

		MaskFormatter maskFormatter= new MaskFormatter(mask);
		maskFormatter.setValueContainsLiteralCharacters(false);

		return maskFormatter.valueToString(numeroformatado) ;
	}
	
	public static void main(String[] args) throws ParseException {
		System.out.println(formatarAgencia("1234", "56789"));
	}
}
