package com.attusVaga.Teste.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.attusVaga.Teste.Entidades.Endereco;

public interface EnderecoRepositorio extends JpaRepository<Endereco, Long>{
	
	@Query("select t from Endereco t where t.pessoa.id = ?1")
	List<Endereco> buscarEnderecoPorPessoa(Long id);

}
