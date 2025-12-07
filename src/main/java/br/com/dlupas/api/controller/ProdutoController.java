package br.com.dlupas.api.controller;

import br.com.dlupas.api.model.Produto;
import br.com.dlupas.api.model.Promocao;
import br.com.dlupas.api.service.ProdutoService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public Produto cadastrar(@RequestBody Produto produto) {
        return produtoService.cadastrarProduto(produto);
    }

    @GetMapping
    public List<Produto> listar() {
        return produtoService.listarProdutos();
    }

    @GetMapping("/{id}")
    public Produto buscar(@PathVariable String id) {
        Produto p = produtoService.buscarPorId(id);
        if (p == null) {
            throw new RuntimeException("Produto não encontrado");
        }
        return p;
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable String id, @RequestBody Produto produto) {
        Produto atualizado = produtoService.atualizarProduto(id, produto);

        if (atualizado == null) {
            throw new RuntimeException("Produto não encontrado");
        }
        return atualizado;
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable String id) {
        boolean removido = produtoService.removerProduto(id);

        if (!removido) {
            throw new RuntimeException("Produto não encontrado");
        }
        return "Produto removido com sucesso!";
    }

    @GetMapping("/{id}/promocoes")
    public List<Promocao> listarPromocoes(@PathVariable String id) {
        Produto produto = produtoService.buscarPorId(id);

        if (produto == null) {
            throw new RuntimeException("Produto não encontrado");
        }

        return produtoService.obterPromocoesAtivas(produto);
    }
}
