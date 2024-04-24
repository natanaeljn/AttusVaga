package com.attusVaga.Teste.Servicos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import com.attusVaga.Excecao.ExcecaoPessoa;
import com.attusVaga.Teste.Entidades.Pessoa;
import com.attusVaga.Teste.Repositorios.PessoaRepositorio;
import com.attusVaga.Teste.Servicos.PessoaServicos;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class PessoaServicosTest {

	@InjectMocks
	PessoaServicos pessoaServicos;

	@Mock
	PessoaRepositorio pessoaRepositorio;
	
	

	private Pessoa pessoa;

	@BeforeEach
	public void setUp() {
		LocalDate data = LocalDate.parse("2023-09-26");
		pessoa = new Pessoa(1L, "testando", data);
	}

	@Test
	void deveBuscarPessoasPorNome() {
		when(pessoaRepositorio.findBynome(pessoa.getNome())).thenReturn(Collections.singletonList(pessoa));
	    List<Pessoa>pessoas=pessoaServicos.consultarPessoaNome(pessoa.getNome());
	    /*testando para ver se o metodo esta retornando oque estamos esperando*/
	    assertEquals(Collections.singletonList(pessoa), pessoas);
	    /*verificamos se o repositorio e chamado apenas uma vez */
	    verify(pessoaRepositorio).findBynome(pessoa.getNome());
	    /*verifica se ele nao e chamado mais de uma vez*/
	    verifyNoMoreInteractions(pessoaRepositorio);
	}

	/*
	 * verificação para ver se ele esta caindo na parte onde valida se foi passado o
	 * nome para validação e se ele esta trazendo a maensagem que esperamos
	 */
	@Test
	void naoDeveChamarORepositorioCasoNomeSejaVazio() {
		ExcecaoPessoa excecao = assertThrows(ExcecaoPessoa.class, () -> pessoaServicos.consultarPessoaNome(""));
		assertTrue(excecao.getMessage().matches(".*Erro ao realizar a consulta,verifique o nome passado.*"));

		excecao = assertThrows(ExcecaoPessoa.class, () -> pessoaServicos.consultarPessoaNome(null));
		assertTrue(excecao.getMessage().matches(".*Erro ao realizar a consulta,verifique o nome passado.*"));

		verifyNoInteractions(pessoaRepositorio);
	}

	/* teste para verificar a busca por id */
	@Test
	void deveBuscarPessoasPorId() {
		when(pessoaRepositorio.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
	    Pessoa pessoaTeste = pessoaRepositorio.findById(pessoa.getId()).get();
	    /*testando para ver se o metodo esta retornando o mesmo usuario*/
	    assertEquals(pessoa, pessoaTeste);
	    /*verificamos se o repositorio e chamado apenas uma vez com o Id */
	    verify(pessoaRepositorio, times(1)).findById(pessoa.getId());
	    /*verifica se ele nao e chamado mais de uma vez*/
	    verifyNoMoreInteractions(pessoaRepositorio);
	}

	/* verificação para ver a validação do id caso ser nulo */
	@Test
	void naoDeveChamarORepositorioCasoIdSejaNulo() {
		ExcecaoPessoa excecao = assertThrows(ExcecaoPessoa.class, () -> pessoaServicos.consultarPessoaId(null));
		assertTrue(excecao.getMessage().matches(".*Erro ao realizar a consulta,verifique seu ID.*"));
		verifyNoInteractions(pessoaRepositorio);
	}

	/* teste para criação de uma nova pessoa */
	@Test
	void testCriarPessoa() {
		Pessoa pessoaCria = new Pessoa();
		pessoaCria.setNome("testeCriação");
		/* chamando o comportamento do repositório para retornar a pessoa criada */
		when(pessoaRepositorio.save(any(Pessoa.class))).thenReturn(pessoaCria);
		Pessoa pessoaCriada = pessoaServicos.criarPessoa(pessoaCria);

		/* Verificando se o método retorna a pessoa esperada */
		assertEquals(pessoaCria, pessoaCriada);

		/* verificando a chamada do repositorio */
		verify(pessoaRepositorio, times(1)).save(pessoaCria);
		verifyNoMoreInteractions(pessoaRepositorio);
	}

	@Test
	    void testEditarPessoa() {
	        /* busca da pessoa no repositório*/
	        when(pessoaRepositorio.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
            Pessoa pessoaEditada = new Pessoa();
	        pessoaEditada.setId(pessoa.getId());
	        pessoaEditada.setNome("TesteEditado");
	        pessoaEditada.setDataNascimento(pessoa.getDataNascimento());
            /* Simular a atualização da pessoa no repositório*/
	        when(pessoaRepositorio.save(any(Pessoa.class))).thenReturn(pessoaEditada);
            Pessoa pessoaAtt = pessoaServicos.editarPessoa(pessoa.getId(), pessoaEditada);
            /* Verificar se o método retorna a pessoa editada*/
	        assertEquals(pessoaEditada, pessoaAtt);
            verify(pessoaRepositorio, times(1)).findById(pessoa.getId());
	        verify(pessoaRepositorio, times(1)).save(pessoa);
	        verifyNoMoreInteractions(pessoaRepositorio);
	    }

}
