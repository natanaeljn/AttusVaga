package com.attusVaga.Teste.Servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.attusVaga.Excecao.ExcecaoPessoa;
import com.attusVaga.Teste.Entidades.Pessoa;
import com.attusVaga.Teste.Repositorios.PessoaRepositorio;

@Service
public class PessoaServicos {

	@Autowired
	private PessoaRepositorio pessoaRepositorio;

	public Pessoa criarPessoa(Pessoa pessoa) {
		return pessoaRepositorio.save(pessoa);
	}

	public Pessoa editarPessoa(Long id, Pessoa pessoa) {
		Pessoa pessoaEdit = pessoaRepositorio.findById(id).get();
		pessoaEdit.setNome(pessoa.getNome());
		pessoaEdit.setDataNascimento(pessoa.getDataNascimento());
		return pessoaRepositorio.save(pessoaEdit);
	}

	public List<Pessoa> consultarPessoaNome(String nome) {
		try {
			Assert.hasText(nome, "nome nao pode estar vazio ");
			Assert.notNull(nome, "nome nao pode estar vazio ");

			return pessoaRepositorio.findBynome(nome);
		} catch (Exception e) {
			throw new ExcecaoPessoa("Erro ao realizar a consulta,verifique o nome passado");
		}
	}

	public Pessoa consultarPessoaId(Long id) {
		try {
			Assert.notNull(id, "o Id nao pode ser nulo");

			return pessoaRepositorio.findById(id).get();
		} catch (Exception e) {
			throw new ExcecaoPessoa("Erro ao realizar a consulta,verifique seu ID");
		}
	}

}
