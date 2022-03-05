package br.com.contabancaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.contabancaria.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ElSE false END FROM Conta c "
    		+ " INNER JOIN c.dadosPessoais d WHERE d.cpf =:cpf AND c.numeroConta =:conta ")
    boolean existeContaAssociada(@Param("cpf") String cpf, @Param("conta") String conta);
    
    @Query("SELECT FROM Conta c "
    		+ " INNER JOIN c.dadosPessoais d WHERE d.nome =:nome AND c.agencia =:agencia ")
    Conta buscarContaPorNomeEAgencia(@Param("nome") String nome, @Param("agencia") String agencia);
    
    Conta findByNumeroConta(String numeroConta);
}
