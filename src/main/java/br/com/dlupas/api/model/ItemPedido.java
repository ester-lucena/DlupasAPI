package br.com.dlupas.api.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data                   // Gera getters, setters, toString, equals, hashCode
@AllArgsConstructor     // Gera construtor com todos os campos
@NoArgsConstructor      // Gera construtor vazio

public class ItemPedido {

    private int produtoId;
    private int quantidade;
    private double subtotal;
    
}