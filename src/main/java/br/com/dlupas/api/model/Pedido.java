package br.com.dlupas.api.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data                   // Gera getters, setters, toString, equals, hashCode
@AllArgsConstructor     // Gera construtor com todos os campos
@NoArgsConstructor      // Gera construtor vazio

public class Pedido {

    private int id;
    private LocalDateTime data;
    private List<ItemPedido> itens;
    private double valorTotal;

}
