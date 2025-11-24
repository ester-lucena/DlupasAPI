package br.com.dlupas.api.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data                   // Gera getters, setters, toString, equals, hashCode
@AllArgsConstructor     // Gera construtor com todos os campos
@NoArgsConstructor      // Gera construtor vazio

public class Produto {

    private String id;         //UUID;
    private String nome;
    private String categoria;
    private String genero;    // masculino, feminino, unissex
    private ArrayList<String> promocaoID;
    private int estoqueTotal;
    private double preco;     // 2 casas decimais
   
}