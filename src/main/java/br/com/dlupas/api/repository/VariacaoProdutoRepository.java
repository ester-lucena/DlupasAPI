package br.com.dlupas.api.repository;

import br.com.dlupas.api.model.VariacaoProduto;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class VariacaoProdutoRepository {

    private static final String caminhoCSV = "data/variacoes.csv";

    public VariacaoProdutoRepository() {
        inicializarArquivo();
    }

    // cria arquivo e cabeçalho se não existir
    private void inicializarArquivo() {
        File file = new File(caminhoCSV);
        if (!file.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoCSV))) {
                bw.write("id;produtoId;cor;tamanho;quantidadeEstoque;precoBase");
                bw.newLine();
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar arquivo de variacoes: " + e.getMessage());
            }
        }
    }

    // lê todas as variações
    public List<VariacaoProduto> listarProdutos() {
        List<VariacaoProduto> variacoes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoCSV))) {
            String linha = br.readLine(); // ignorar header

            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");

                VariacaoProduto v = new VariacaoProduto();
                v.setId(campos[0]);
                v.setProdutoId(campos[1]);
                v.setCor(campos[2]);
                v.setTamanho(campos[3]);
                v.setQuantidadeEstoque(Integer.parseInt(campos[4]));
                v.setPrecoBase(Double.parseDouble(campos[5]));

                variacoes.add(v);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler variacoes CSV: " + e.getMessage());
        }

        return variacoes;
    }

    // salva todas as variações no CSV
    public void savarTodosProdutos(List<VariacaoProduto> variacoes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoCSV))) {

            bw.write("id;produtoId;cor;tamanho;quantidadeEstoque;precoBase");
            bw.newLine();

            for (VariacaoProduto v : variacoes) {
                bw.write(
                        v.getId() + ";" +
                        v.getProdutoId() + ";" +
                        v.getCor() + ";" +
                        v.getTamanho() + ";" +
                        v.getQuantidadeEstoque() + ";" +
                        v.getPrecoBase()
                );
                bw.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar variacoes CSV: " + e.getMessage());
        }
    }

    // salva uma nova variação
    public VariacaoProduto criarVaricaoProduto(VariacaoProduto variacao) {
        List<VariacaoProduto> variacoes = listarProdutos();

        variacao.setId(UUID.randomUUID().toString());
        variacoes.add(variacao);

        savarTodosProdutos(variacoes);
        return variacao;
    }

    // busca por ID da variação
    public VariacaoProduto encontrarVariacaoID(String id) {
        return listarProdutos().stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // busca todas as variações de um produto específico
    public List<VariacaoProduto> listarVariacoesProdutoId(String produtoId) {
        List<VariacaoProduto> lista = new ArrayList<>();

        for (VariacaoProduto v : listarProdutos()) {
            if (v.getProdutoId().equals(produtoId)) {
                lista.add(v);
            }
        }

        return lista;
    }

    // deletar variação por ID
    public void deletarVariacaoProduto(String id) {
        List<VariacaoProduto> variacoes = listarProdutos();
        variacoes.removeIf(v -> v.getId().equals(id));
        savarTodosProdutos(variacoes);
    }

    // deletar todas as variações de um produto
    public void deletarVariacoesProduto(String produtoId) {
        List<VariacaoProduto> variacoes = listarProdutos();
        variacoes.removeIf(v -> v.getProdutoId().equals(produtoId));
        savarTodosProdutos(variacoes);
    }

    // atualizar variação
    public VariacaoProduto atualizarVariacaoProduto(String id, VariacaoProduto nova) {
        List<VariacaoProduto> variacoes = listarProdutos();

        variacoes.removeIf(v -> v.getId().equals(id));
        nova.setId(id);
        variacoes.add(nova);

        savarTodosProdutos(variacoes);
        return nova;
    }
}
