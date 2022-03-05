package br.com.contabancaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.contabancaria.dto.request.ContaRequestDTO;
import br.com.contabancaria.model.Conta;
import br.com.contabancaria.service.ContaService;

@RestController
@RequestMapping("conta")
public class ContaController {

	@Autowired
	private ContaService contaService;
	
	@PostMapping()
	public ResponseEntity<?> abrirContaBancaria(@RequestBody ContaRequestDTO contaRequestDto) throws Exception{
		contaService.abrirConta(contaRequestDto);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("nome/{nome}/agencia/{agencia}")
	public ResponseEntity<?> buscar(@PathVariable("nome") String nome, @PathVariable("nome") String agencia) throws Exception{
		Conta conta = contaService.buscar(nome, agencia);
		
		return conta != null ? ResponseEntity.ok(conta) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{conta}")
	public ResponseEntity<?> buscar(@PathVariable("conta") String numeroConta) throws Exception{
		Conta conta = contaService.buscar(numeroConta);
		
		return conta != null ? ResponseEntity.ok(conta) : ResponseEntity.noContent().build();
	}
}
