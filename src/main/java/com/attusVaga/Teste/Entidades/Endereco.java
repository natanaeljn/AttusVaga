package com.attusVaga.Teste.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;
	
	private String logradouro;
	
	private String cep;
	
	private Integer numero;
	
	private String cidade;
	
	@ManyToOne
	@JsonBackReference
	private Pessoa pessoa;
	
	private boolean principal;
	
	
	/*metodo para formatar para um boolean*/
	@JsonProperty("principal")
    public void setPrincipal(String principal) {
        this.principal = Boolean.parseBoolean(principal);
    }

}
