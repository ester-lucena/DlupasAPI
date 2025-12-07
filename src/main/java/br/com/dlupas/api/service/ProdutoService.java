package br.com.dlupas.api.service;

import br.com.dlupas.api.model.Produto;
import br.com.dlupas.api.model.VariacaoProduto;
import br.com.dlupas.api.model.Promocao;
import br.com.dlupas.api.repository.ProdutoRepository;
import br.com.dlupas.api.repository.VariacaoProdutoRepository;
import br.com.dlupas.api.repository.PromocaoRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final VariacaoProdutoRepository variacaoRepository;
    private final PromocaoRepository promocaoRepository;

    public ProdutoService(
            ProdutoRepository produtoRepository,
            VariacaoProdutoRepository variacaoRepository,
            PromocaoRepository promocaoRepository
    ) {
        this.produtoRepository = produtoRepository;
        this.variacaoRepository = variacaoRepository;
        this.promocaoRepository = promocaoRepository;
    }

    public Produto cadastrarProduto(Produto produto) {
        if (produto.getPromocaoID() == null) {
            produto.setPromocaoID(new ArrayList<>());
        }

        return produtoRepository.criarProduto(produto);
    }

    public List<Produto> listarProdutos() {
        List<Produto> produtos = produtoRepository.listarProdutos();

        for (Produto p : produtos) {
            List<VariacaoProduto> variacoes = variacaoRepository.listarVariacoesProdutoId(p.getId());
            p.setVariacoes(new ArrayList<>(variacoes));
        }

        return produtos;
    }

    public Produto buscarPorId(String id) {
        Produto produto = produtoRepository.encontrarProdutoID(id);

        if (produto == null) return null;

        List<VariacaoProduto> variacoes = variacaoRepository.listarVariacoesProdutoId(id);
        produto.setVariacoes(new ArrayList<>(variacoes));

        return produto;
    }

    public Produto atualizarProduto(String id, Produto atualizado) {
        Produto existente = produtoRepository.encontrarProdutoID(id);

        if (existente == null) return null;

        // mantém promoções existentes se nova lista for nula
        if (atualizado.getPromocaoID() == null) {
            atualizado.setPromocaoID(existente.getPromocaoID());
        }

        return produtoRepository.atualizarProduto(id, atualizado);
    }

    public boolean removerProduto(String id) {
        Produto existe = produtoRepository.encontrarProdutoID(id);

        if (existe == null) return false;

        // remove as variações associadas
        variacaoRepository.deletarVariacoesProduto(id);

        produtoRepository.deletarProdutoID(id);
        return true;
    }

    public List<Promocao> obterPromocoesAtivas(Produto produto) {
        List<Promocao> lista = new ArrayList<>();

        if (produto.getPromocaoID() == null) return lista;

        for (String promoId : produto.getPromocaoID()) {
            Promocao promo = promocaoRepository.encontrarPromocao(promoId);

            if (promo != null && promo.isAtivo()) {
                lista.add(promo);
            }
        }

        return lista;
    }

    public double calcularPrecoBaseMedio(Produto produto) {
        if (produto.getVariacoes() == null || produto.getVariacoes().isEmpty()) {
            return 0;
        }

        return produto.getVariacoes()
                .stream()
                .mapToDouble(VariacaoProduto::getPrecoBase)
                .average()
                .orElse(0.0);
    }
}

