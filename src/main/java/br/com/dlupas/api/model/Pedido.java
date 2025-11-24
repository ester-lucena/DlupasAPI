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

    private String id; // UUID
    private LocalDateTime data;
    private List<ItemPedido> itens; //fk list
    private double totalDescontos; // 2 casas decimais
    private double valorTotal; // 2 casas decimais
    private String status; // pendente, confirmado, cancelado
    private String cliente;

}
