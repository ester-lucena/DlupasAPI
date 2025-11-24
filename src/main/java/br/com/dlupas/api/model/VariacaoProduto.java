package br.com.dlupas.api.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data                   // Gera getters, setters, toString, equals, hashCode
@AllArgsConstructor     // Gera construtor com todos os campos
@NoArgsConstructor      // Gera construtor vazio

public class VariacaoProduto {
	
	private String id; // UUID
	private String produtoId; // fk
	private String cor;
	private String tamanho; // PP, P, M, G, GG
	private int quantidadeEstoque;
	private double precoBase; // 2 casas decimais
	
}
