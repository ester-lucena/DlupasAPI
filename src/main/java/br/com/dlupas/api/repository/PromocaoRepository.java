package br.com.dlupas.api.repository;

import br.com.dlupas.api.model.Promocao;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class PromocaoRepository {

    private static final String caminhoCSV = "data/promocoes.csv";

    public PromocaoRepository() {
        inicializarArquivo();
    }

    // Cria o arquivo CSV com cabeçalho
    private void inicializarArquivo() {
        File file = new File(caminhoCSV);

        if (!file.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoCSV))) {
                bw.write("id;descricao;tipo;valor;ativo");
                bw.newLine();
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar arquivo de promocoes: " + e.getMessage());
            }
        }
    }

    // Lê todas promoções
    public List<Promocao> listarProdutos() {
        List<Promocao> promocoes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoCSV))) {
            String linha = br.readLine(); // Ignora cabeçalho

            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");

                Promocao p = new Promocao();
                p.setId(campos[0]);
                p.setDescricao(campos[1]);
                p.setTipo(campos[2]);
                p.setValor(Double.parseDouble(campos[3]));
                p.setAtivo(Boolean.parseBoolean(campos[4]));

                promocoes.add(p);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler promocoes CSV: " + e.getMessage());
        }

        return promocoes;
    }

    // Salva todas promoções no CSV
    public void salvarTodasPromocoes(List<Promocao> promocoes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoCSV))) {

            bw.write("id;descricao;tipo;valor;ativo");
            bw.newLine();

            for (Promocao p : promocoes) {
                bw.write(
                    p.getId() + ";" +
                    p.getDescricao() + ";" +
                    p.getTipo() + ";" +
                    p.getValor() + ";" +
                    p.isAtivo()
                );
                bw.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar promocoes CSV: " + e.getMessage());
        }
    }

    // Salva uma nova promoção
    public Promocao criarPromocao(Promocao promocao) {
        List<Promocao> promocoes = listarProdutos();

        promocao.setId(UUID.randomUUID().toString());
        promocoes.add(promocao);

        salvarTodasPromocoes(promocoes);
        return promocao;
    }

    // Busca por ID
    public Promocao encontrarPromocao(String id) {
        return listarProdutos()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Deleta por ID
    public void deletarPromocao(String id) {
        List<Promocao> lista = listarProdutos();
        lista.removeIf(p -> p.getId().equals(id));
        salvarTodasPromocoes(lista);
    }

    // Atualiza promoção
    public Promocao atualizarPromocao(String id, Promocao novaPromocao) {
        List<Promocao> lista = listarProdutos();

        lista.removeIf(p -> p.getId().equals(id));
        novaPromocao.setId(id);
        lista.add(novaPromocao);

        salvarTodasPromocoes(lista);
        return novaPromocao;
    }
}

