package br.com.contabancaria.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.contabancaria.model.Conta;
import br.com.contabancaria.repository.ContaRepository;
import br.com.contabancaria.service.ContaServiceImpl;
import lombok.extern.log4j.Log4j2;
import net.javacrumbs.shedlock.core.SchedulerLock;

@Log4j2
@Configuration
public class AplicarTaxaChequeEspecialJob {
	
	@Autowired
	private ContaRepository repository;
	
	private final static String HORARIO_EXECUCAO = "0 0/1 * * * ?";


	@Scheduled(cron = HORARIO_EXECUCAO)
	@SchedulerLock(name = "aplicarTaxa")
	public void aplicarTaxa() {
		List<Conta> contas = repository.findAll();
		
		contas.stream().forEach(conta -> {
			
			if (conta.isChequeEspecialAtivo()) {
				log.info("Aplicando taxa de juros para a conta : {}", conta.getNumeroConta());
				Double taxa = conta.getChequeEspecial().getTaxa();
				Double saldoAcumulado = conta.getChequeEspecial().getSaldoAcumulado();

				double saldoComTaxa = saldoAcumulado + (taxa * saldoAcumulado);
				conta.getChequeEspecial().setSaldoAcumulado(saldoComTaxa);
				
				Conta save = repository.save(conta);
				log.info("Saldo {}", save.getChequeEspecial().getSaldoAcumulado());
			}
		});
	}
}
