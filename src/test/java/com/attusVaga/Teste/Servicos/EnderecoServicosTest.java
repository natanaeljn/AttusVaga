package com.attusVaga.Teste.Servicos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.attusVaga.Excecao.ExcecaoEndereco;
import com.attusVaga.Teste.Entidades.Endereco;
import com.attusVaga.Teste.Entidades.Pessoa;
import com.attusVaga.Teste.Repositorios.EnderecoRepositorio;
import com.attusVaga.Teste.Repositorios.PessoaRepositorio;
import com.attusVaga.Teste.Servicos.EnderecoServicos;

@ExtendWith(MockitoExtension.class)
public class EnderecoServicosTest {

	@InjectMocks
	EnderecoServicos enderecoServicos;

	@Mock
	PessoaRepositorio pessoaRepositorio;

	@Mock
	EnderecoRepositorio enderecoRepositorio;

	private Pessoa pessoa;

	private Endereco endereco;

	@BeforeEach
	public void setUp() {
		LocalDate data = LocalDate.parse("2023-09-26");
		pessoa = new Pessoa(1L, "testando", data);
		this.endereco = new Endereco(1L, "teste", "891020-060", 510, "Rio do Sul", pessoa, true);
	}

	@Test
	    void testeParaCriarEndereco() {
	        /* busca da pessoa no repositorio*/
	        when(pessoaRepositorio.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
            /* usando o metodo de salvar e testando*/
	        when(enderecoRepositorio.save(endereco)).thenReturn(endereco);
            Endereco enderecoCriado = enderecoServicos.criarEndereco(pessoa.getId(), endereco);
            /*comparando para ver se esta retornando o mesmo endereco corretamente*/
	        assertEquals(endereco, enderecoCriado);
	        /*comparando a pessoa , par ver se esta correta*/
	        assertEquals(pessoa, enderecoCriado.getPessoa());
            /*teste das chamadas do repositorio*/
	        verify(pessoaRepositorio, times(1)).findById(pessoa.getId());
	        verify(enderecoRepositorio, times(1)).save(endereco);
	        verifyNoMoreInteractions(pessoaRepositorio, enderecoRepositorio);
	    }

	/* verificação para ver a validação do id caso ser nulo */
	@Test
	void naoDeveChamarORepositorioCasoIdSejaNulo() {
		ExcecaoEndereco excecao = assertThrows(ExcecaoEndereco.class,
				() -> enderecoServicos.criarEndereco(null, endereco));
		assertTrue(excecao.getMessage().matches(
				".*Erro ao criar novo endereço , verifique se a Pessoa que voçe esta passando esta cadastrada.*"));
		verifyNoInteractions(pessoaRepositorio);
		verifyNoInteractions(enderecoRepositorio);
	}

	/* teste da listagem de endereços */
	@Test
	void deveBuscarEnderecosPorIdDaPessoa() {
		when(enderecoRepositorio.buscarEnderecoPorPessoa(pessoa.getId())).thenReturn(Collections.singletonList(endereco));
	    List<Endereco>enderecos=enderecoServicos.listarEnderecos(pessoa.getId());
	    assertEquals(Collections.singletonList(endereco), enderecos);
	    verify(enderecoRepositorio).buscarEnderecoPorPessoa(pessoa.getId());
	    verifyNoMoreInteractions(enderecoRepositorio);
	}
	
	@Test
	void naoDeveChamarORepositorioCasoIdSejaNuloNaListagem() {
		ExcecaoEndereco excecao = assertThrows(ExcecaoEndereco.class,
				() -> enderecoServicos.listarEnderecos(null));
		assertTrue(excecao.getMessage().matches(
				".*Erro ao realizar a consulta  , verifique se a pessoa se o ID esta correto.*"));
		verifyNoInteractions(pessoaRepositorio);
		verifyNoInteractions(enderecoRepositorio);
	}
	
	
}
