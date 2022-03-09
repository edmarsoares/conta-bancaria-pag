package br.com.contabancaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.contabancaria.dto.request.ContaRequestDTO;
import br.com.contabancaria.dto.request.DepositoContaRequestDTO;
import br.com.contabancaria.dto.request.FiltroRequestDTO;
import br.com.contabancaria.dto.request.SaqueContaRequestDTO;
import br.com.contabancaria.dto.request.SaqueResponseDTO;
import br.com.contabancaria.dto.response.ContaResponseDTO;
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
	
	@PostMapping("/deposito")
	public ResponseEntity<?> depositar(@RequestBody DepositoContaRequestDTO deposito) throws Exception{
		contaService.depositar(deposito);
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/saque")
	public ResponseEntity<?> sacar(@RequestBody SaqueContaRequestDTO saque) throws Exception{
		SaqueResponseDTO saqueResponseDto =  contaService.sacar(saque);
		
		return ResponseEntity.ok(saqueResponseDto);
	}
	
	@PostMapping("filtro")
	public ResponseEntity<?> buscaFiltrada(@RequestBody FiltroRequestDTO filter) throws Exception{
		Conta conta = contaService.buscaFiltrada(filter);
		
		return conta != null ? ResponseEntity.ok(conta) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{conta}")
	public ResponseEntity<?> buscar(@PathVariable("conta") String numeroConta) throws Exception{
		ContaResponseDTO conta = contaService.buscar(numeroConta);
		
		return conta != null ? ResponseEntity.ok(conta) : ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerConta(@PathVariable("id") Long id) throws Exception{
		contaService.removerConta(id);
		
		return ResponseEntity.noContent().build();
	}
}
