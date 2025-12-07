package br.com.dlupas.api.repository;

import br.com.dlupas.api.model.Produto;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ProdutoRepository {

	private static final String caminhoCSV = "data/produtos.csv";
	
	public ProdutoRepository() {
        inicializarArquivo();
    }
	
	public void inicializarArquivo() {
		File arquivo = new File(caminhoCSV);
		if(!arquivo.exists()) {
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoCSV))){
				bw.write("id;nome;categoria;genero;promocaoID");
				bw.newLine();
			} catch(IOException e){
				throw new RuntimeException("Erro ao criar arquivo de produtos: " + e.getMessage());
			}
		}
	}
	
	public void salvarTodosProdutos(List<Produto> produtos) {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoCSV))){
			bw.write("id;nome;categoria;genero;promocaoID");
			bw.newLine();
			
			for (Produto p : produtos) {
                String promocoes = String.join(",", p.getPromocaoID() == null ? new ArrayList<>() : p.getPromocaoID());

                bw.write(
                        p.getId() + ";" +
                        p.getNome() + ";" +
                        p.getCategoria() + ";" +
                        p.getGenero() + ";" +
                        promocoes
                );
                bw.newLine();
            }
		} catch(IOException e){
			throw new RuntimeException("Erro ao salvar arquivo CSV: " + e.getMessage());
		}
	}
	
	public List<Produto> listarProdutos(){
		List<Produto> produtos = new ArrayList();
		
		try(BufferedReader br = new BufferedReader(new FileReader(caminhoCSV))){
			String linha = br.readLine();
			
			while((linha = br.readLine()) != null) {
				String[] campos = linha.split(";");
				
				Produto p = new Produto();
                p.setId(campos[0]);
                p.setNome(campos[1]);
                p.setCategoria(campos[2]);
                p.setGenero(campos[3]);
                
                // Converte lista de promoções
                ArrayList<String> promocoes = new ArrayList<>();
                if (campos.length > 4 && !campos[4].isEmpty()) {
                    for (String promo : campos[4].split(",")) {
                        promocoes.add(promo);
                    }
                }
                p.setPromocaoID(promocoes);

                produtos.add(p);
			}
		} catch(IOException e){
			throw new RuntimeException("Erro ao ler produtos CSV: " + e.getMessage());
		}
		return produtos;
	}
	
	public Produto criarProduto(Produto produto) {
		List<Produto> produtos = listarProdutos();
		
		produto.setId(UUID.randomUUID().toString());
        produtos.add(produto);

        salvarTodosProdutos(produtos);
		return produto;
	}
	
	public Produto encontrarProdutoID(String id) {
	    return listarProdutos().stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
	}

	public void deletarProdutoID(String id) {
        List<Produto> produtos = listarProdutos();
        produtos.removeIf(p -> p.getId().equals(id));
        salvarTodosProdutos(produtos);
        
    }
	
	public Produto atualizarProduto(String id, Produto novoProduto) {
        List<Produto> produtos = listarProdutos();

        produtos.removeIf(p -> p.getId().equals(id));
        novoProduto.setId(id);
        produtos.add(novoProduto);

        salvarTodosProdutos(produtos);
        return novoProduto;
    }
}
