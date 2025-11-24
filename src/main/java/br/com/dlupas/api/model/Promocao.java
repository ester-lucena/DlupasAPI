package br.com.dlupas.api.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data                   // Gera getters, setters, toString, equals, hashCode
@AllArgsConstructor     // Gera construtor com todos os campos
@NoArgsConstructor      // Gera construtor vazio

public class Promocao {
	
	private String id; // nome da promoção
	private String descricao;
	private String tipo; // porcentagem ou combo
	private double valor; // zero caso o tipo seja combo
	private boolean ativo;
	
}
