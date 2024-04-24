package com.attusVaga.Teste.Controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.attusVaga.Teste.Entidades.Pessoa;
import com.attusVaga.Teste.Servicos.PessoaServicos;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoa")
public class PessoaControle {

	@Autowired
	private PessoaServicos pessoaServicos;

	@PostMapping("/criarPessoa")
	public ResponseEntity<Pessoa> criarPessoa(@Valid @RequestBody Pessoa pessoa) {
		return ResponseEntity.ok(this.pessoaServicos.criarPessoa(pessoa));
	}

	@PostMapping("/editarPessoa")
	public ResponseEntity<Pessoa> editarPessoa(@RequestParam("id") Long id,@Valid @RequestBody Pessoa pessoa) {
		return  ResponseEntity.ok(this.pessoaServicos.editarPessoa(id, pessoa));
	}

	@GetMapping("/consultarNome")
	public ResponseEntity< List<Pessoa>> consultarPessoaNome(@RequestParam("nome") String nome) {
		List<Pessoa>lista =  this.pessoaServicos.consultarPessoaNome(nome);
		if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna 404 Not Found se a lista está vazia
        } else {
			return ResponseEntity.status(HttpStatus.OK).body(lista); 
        }
    
	}

	@GetMapping("/consultarPessoa")
	public Pessoa consultarPessoa(@RequestParam("id") Long id) {
		return this.pessoaServicos.consultarPessoaId(id);
	}

}
