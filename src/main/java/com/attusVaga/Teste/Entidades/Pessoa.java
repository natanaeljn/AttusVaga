package com.attusVaga.Teste.Entidades;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "nome nao pode estar em branco")
	@NotNull(message = "nome nao pode estar vazio")
	private String nome;
	@NotNull(message = "A data de nascimento não pode ser nula")
	private LocalDate dataNascimento;

	@OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Endereco> endereco;
	
	
	public  Pessoa(long l, String nome, LocalDate data) {
         this.id = l ;
         this.nome = nome;
         this.dataNascimento= data;
	}

}
