package br.com.contabancaria.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyBoolean;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.contabancaria.dto.request.ContaRequestDTO;
import br.com.contabancaria.dto.request.DadosPessoaisRequestDTO;
import br.com.contabancaria.dto.request.DepositoContaRequestDTO;
import br.com.contabancaria.dto.request.EnderecoRequestDTO;
import br.com.contabancaria.dto.request.FiltroRequestDTO;
import br.com.contabancaria.model.Banco;
import br.com.contabancaria.model.ChequeEspecial;
import br.com.contabancaria.model.Conta;
import br.com.contabancaria.model.DadosPessoais;
import br.com.contabancaria.model.StatusChequeEspecial;
import br.com.contabancaria.model.TipoConta;
import br.com.contabancaria.repository.ContaRepository;
import br.com.contabancaria.util.Utils;

public class ContaServiceImplTest {

	@InjectMocks
	private ContaServiceImpl service;

	@Mock
    private ContaRepository repository;
	
	@Mock
	private MovimentacaoService movimentacaoService;
	
	@BeforeEach
	public void setup() {	
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void abrirContaJaExistenteTest() {
		when(repository.existeContaAssociada(anyString(), anyString())).thenReturn(Boolean.TRUE);
		ContaRequestDTO contaDto = ContaRequestDTO.builder().build();
		
		assertThrows(Exception.class, () -> service.abrirConta(contaDto));
	}
	
	@Test
	public void abrirContaTest() throws Exception {
		when(repository.existeContaAssociada(anyString(), anyString())).thenReturn(Boolean.FALSE);
		when(repository.save(any())).thenReturn(new Conta());

		ContaRequestDTO contaDto = gerarContaRequestMock();
		
		assertDoesNotThrow(() -> service.abrirConta(contaDto));
	}
	
	@Test
	public void depositarTest() throws Exception {
		
		when(repository.buscarContaPorCpfIdBancoENumeroConta(anyString(), anyString(), anyString()))
			.thenReturn(Optional.ofNullable(gerarContaMock()));
		
		when(repository.save(any())).thenReturn(new Conta());
		
		DepositoContaRequestDTO depositoContaRequestDTO = DepositoContaRequestDTO.builder()
		.cpf("09445546")
		.numeroConta("123456")
		.banco(Banco.BANCO_SANTANDER)
		.valor(500.0)
		.build();
		
		service.depositar(depositoContaRequestDTO);
		assertDoesNotThrow(() -> service.depositar(depositoContaRequestDTO));
	}
	
	@Test
	public void buscaFiltradaTest() throws Exception {
		Conta conta = gerarContaMock();
		when(repository.buscarContaPorNomeEAgencia(anyString(), anyString(), anyBoolean())).thenReturn(conta);

		FiltroRequestDTO filtroRequestDTO = FiltroRequestDTO
			.builder()
			.agencia(Banco.BANCO_BRADESCO.gerarNumeroAgencia())
			.nome("Edmar Soares")
			.chequeEspecialAtivo(Boolean.TRUE)
			.build();
		
		assertNotNull(service.buscaFiltrada(filtroRequestDTO));
	}
	
	@Test
	public void buscaContaPorNumeroTest() throws Exception {
		Mockito.mockStatic(Utils.class);

		Conta conta = gerarContaMock();
		when(repository.findByNumeroConta(anyString())).thenReturn(Optional.ofNullable(conta));
		
		when(Utils.formatarAgencia(anyString(), anyString())).thenReturn("1234/5678-9");
		when(Utils.formatarMoeda(any())).thenReturn("R$ 500,00");

		
		assertNotNull(service.buscar("123456"));
	}
	
	@AfterEach
	public void after() {
		Mockito.clearAllCaches();
	}

	private Conta gerarContaMock() {
		DadosPessoais dadosPessoais = DadosPessoais.builder()
				.cpf("12345678911")
				.nomeCompleto("Edmar soares de lima")
				.build();
		
		ChequeEspecial chequeEspecial = ChequeEspecial.builder()
				.statusChequeEspecial(StatusChequeEspecial.NAO_LIBERADO)
				.valorDisponivel(500.0)
				.saldoAcumulado(0.0)
				.build();
		
		Conta conta = Conta.builder()
				.agencia(Banco.BANCO_BRADESCO.gerarNumeroAgencia())
				.saldo(500.0)
				.chequeEspecialAtivo(Boolean.FALSE)
				.idBanco(Banco.BANCO_BRADESCO.getIdentificadorBanco())
				.numeroConta(Banco.BANCO_BRADESCO.gerarNumeroConta())
				.dadosPessoais(dadosPessoais)
				.chequeEspecial(chequeEspecial)
				.build();
		
		return conta;
	}

	private ContaRequestDTO gerarContaRequestMock() {
		EnderecoRequestDTO enderecoRequestDTO = EnderecoRequestDTO
				.builder()
				.cep("58105508")
				.cidade("Cabedelo")
				.estado("PB")
				.rua("SantoAntonio")
				.build();
		
		DadosPessoaisRequestDTO dadosPessoaisDto = DadosPessoaisRequestDTO.
				builder()
				.cpf("12312313")
				.dataNascimento(LocalDate.now())
				.idade("30")
				.nomeCompleto("Edmar Soares")
				.endereco(enderecoRequestDTO)
				.build();
		
		ContaRequestDTO contaDto = ContaRequestDTO.builder()
				.banco(Banco.BANCO_BRADESCO)
				.saldoInicial(500.0)
				.tipoConta(TipoConta.CORRENTE)
				.dadosPessoais(dadosPessoaisDto)
				.build();
		
		return contaDto;
	}
}
