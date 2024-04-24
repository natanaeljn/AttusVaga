package com.attusVaga.Teste.Controles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.attusVaga.Teste.Controles.PessoaControle;
import com.attusVaga.Teste.Entidades.Pessoa;
import com.attusVaga.Teste.Servicos.PessoaServicos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
public class PessoaControleTest {

	@InjectMocks
	PessoaControle pessoaControle;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	PessoaServicos pessoaServicos;

	MockMvc mockMvc;

	/* trazendo todas as classes e parametros presentes no controle */
	private Pessoa pessoa;

	private String nomeBuscar;
	private List<Pessoa> listaPessoas;

	@BeforeEach
	public void setup() throws JsonProcessingException {
		/* preparando o mock para trabalhar com o controle */
		mockMvc = MockMvcBuilders.standaloneSetup(pessoaControle).alwaysDo(print()).build();

		LocalDate data = LocalDate.parse("2023-09-26");
		pessoa = new Pessoa(1L, "testando", data);

		nomeBuscar = "Teste1Lista";

		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		listaPessoas = new ArrayList<>();
		listaPessoas.add(new Pessoa(1L, "Teste1Lista", data));
		listaPessoas.add(new Pessoa(2L, "Teste2Lista", data));

	}

	@Test
	void criarPessoaTeste() throws Exception {
		String requestBody = objectMapper.writeValueAsString(this.pessoa);
		/* testando se o serviço esta trazendo a pessoa criada */
		when(pessoaServicos.criarPessoa(any(Pessoa.class))).thenReturn(pessoa);
		/* EndPoint e passando o corpo da requisição */
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/pessoa/criarPessoa")
				.contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isOk());

		/* testando se o nome e correspondente e validando a pessoa */
		resultActions.andExpect(jsonPath("$.nome").value(pessoa.getNome()));
	}

	@Test
	void editarPessoaTeste() throws Exception {
		Pessoa pessoaAtualizada = new Pessoa(pessoa.getId(), "NovoNome", pessoa.getDataNascimento());

		/* testando o serviço para passar a pessoaAtt */
		when(pessoaServicos.editarPessoa(any(Long.class), any(Pessoa.class))).thenReturn(pessoaAtualizada);

		String requestBody = objectMapper.writeValueAsString(pessoaAtualizada);
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/pessoa/editarPessoa")
				.param("id", pessoa.getId().toString()).contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isOk());
		/* Verificar se a resposta contém o nome atualizado */
		resultActions.andExpect(jsonPath("$.nome").value(pessoaAtualizada.getNome()));
		/* Verificar se o serviço foi chamado corretamente */
		verify(pessoaServicos).editarPessoa(any(Long.class), any(Pessoa.class));
	}

	@Test
    void consultarPessoaPorNomeListaTeste() throws Exception {
        when(pessoaServicos.consultarPessoaNome(nomeBuscar)).thenReturn(listaPessoas);
        
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/pessoa/consultarNome")
                .param("nome", nomeBuscar)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        /*verificando se a lista corresponde, primeiro pelo tamanho  , seguidamente pelos nomes das pessoas*/
        resultActions.andExpect(jsonPath("$.length()").value(listaPessoas.size()));
        resultActions.andExpect(jsonPath("$[0].nome").value("Teste1Lista"));
        resultActions.andExpect(jsonPath("$[1].nome").value("Teste2Lista"));
    }

	@Test
	void consultarPessoaPorIdTeste() throws Exception {
	        
	        when(pessoaServicos.consultarPessoaId(pessoa.getId())).thenReturn(pessoa);
       
	        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/pessoa/consultarPessoa")
	                .param("id", pessoa.getId().toString())
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.id").value(1))
	                .andExpect(jsonPath("$.nome").value("testando"));

	        verify(pessoaServicos).consultarPessoaId(pessoa.getId());
	    }

}
