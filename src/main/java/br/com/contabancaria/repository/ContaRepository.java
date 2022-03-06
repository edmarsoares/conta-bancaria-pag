package br.com.contabancaria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.contabancaria.model.Banco;
import br.com.contabancaria.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ElSE false END FROM Conta c "
    		+ " INNER JOIN c.dadosPessoais d WHERE d.cpf =:cpf AND c.idBanco =:idBanco ")
    boolean existeContaAssociada(@Param("cpf") String cpf, @Param("idBanco") String idBanco);
    
    @Query("SELECT c FROM Conta c "
    		+ " INNER JOIN c.dadosPessoais d WHERE d.nomeCompleto LIKE CONCAT('%',:nome,'%') AND c.agencia =:agencia ")
    Conta buscarContaPorNomeEAgencia(@Param("nome") String nome, @Param("agencia") String agencia);
    
    @Query("SELECT c FROM Conta c "
    		+ " INNER JOIN c.dadosPessoais d WHERE d.cpf =:cpf AND c.idBanco =:idBanco AND c.numeroConta =:conta ")
    Optional<Conta> buscarContaPorCpfIdBancoENumeroConta(@Param("cpf") String cpf, @Param("idBanco") String idBanco, @Param("conta") String conta);
    
    Conta findByNumeroConta(String numeroConta);
}
