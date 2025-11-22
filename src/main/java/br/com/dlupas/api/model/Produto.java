package br.com.dlupas.api.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data                   // Gera getters, setters, toString, equals, hashCode
@AllArgsConstructor     // Gera construtor com todos os campos
@NoArgsConstructor      // Gera construtor vazio

public class Produto {

    private int id;
    private String modelo;
    private String categoria;
    private String tamanho;
    private String cor;
    private double preco;
    private int quantidadeEstoque;
    private boolean promocaoAtiva;

}