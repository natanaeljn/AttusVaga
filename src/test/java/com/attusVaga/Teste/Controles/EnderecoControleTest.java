package com.attusVaga.Teste.Controles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

import com.attusVaga.Teste.Controles.EnderecoControle;
import com.attusVaga.Teste.Entidades.Endereco;
import com.attusVaga.Teste.Entidades.Pessoa;
import com.attusVaga.Teste.Servicos.EnderecoServicos;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class EnderecoControleTest {

	@InjectMocks
	EnderecoControle enderecoControle;

	@Mock
	EnderecoServicos enderecoServicos;

	@Autowired
	private ObjectMapper objectMapper;

	MockMvc mockMvc;

	private Pessoa pessoa;

	private Endereco endereco;
	
	private List<Endereco> listaEndereco;

	@BeforeEach
	public void setup() {
		/* preparando o mock para trabalhar com o controle */
		mockMvc = MockMvcBuilders.standaloneSetup(enderecoControle).alwaysDo(print()).build();

		LocalDate data = LocalDate.parse("2023-09-26");
		pessoa = new Pessoa(1L, "testando", data);

		endereco = new Endereco(1L, "teste", "891020-060", 510, "Rio do Sul", pessoa, true);

		objectMapper = new ObjectMapper();
		
		listaEndereco = new ArrayList<>();
		listaEndereco.add(new Endereco(1L, "teste", "891020-060", 510, "Rio do Sul", pessoa, true));
		listaEndereco.add(new Endereco(2L, "teste", "891020-060", 510, "Rio do Sul", pessoa, false));

	}

	@Test
	void criarEnderecoTeste() throws Exception {
		Long idPessoa = pessoa.getId();
		String requestBody = objectMapper.writeValueAsString(endereco);

		when(enderecoServicos.criarEndereco(eq(idPessoa), any(Endereco.class))).thenReturn(endereco);
		ResultActions resultActions = mockMvc
				.perform(MockMvcRequestBuilders.post("/endereco/criarEndereco")
						.param("idPessoa", idPessoa.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(endereco.getId()));
		
		verify(enderecoServicos).criarEndereco(eq(idPessoa), any(Endereco.class));
	}
	
	@Test
    void consultarEnderecoPorIdListaTeste() throws Exception {
        when(enderecoServicos.listarEnderecos(pessoa.getId())).thenReturn(listaEndereco);
        
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/endereco/listarEnderecos")
                .param("id", pessoa.getId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        /*verificando se a lista corresponde, primeiro pelo tamanho  , seguidamente pelos ceps dos enderecos*/
        resultActions.andExpect(jsonPath("$.length()").value(listaEndereco.size()));
        resultActions.andExpect(jsonPath("$[0].cep").value("891020-060"));
        resultActions.andExpect(jsonPath("$[1].cep").value("891020-060"));
    }
	

}
