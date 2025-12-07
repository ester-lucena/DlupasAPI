```markdown
# 📦 DlupasAPI – Sistema de Gerenciamento de Produtos, Variações e Promoções
Projeto desenvolvido para a disciplina **Linguagem de Programação II** do curso de **Bacharelado em Tecnologia da Informação** da **Universidade Federal do Rio Grande do Norte (UFRN)**, ofertada pelo **Instituto Metrópole Digital (IMD)**.

**Professor:** João Anisio Marinho da Nóbrega  
**Autores:**  
- Ester de Lucena Paiva  
- Filipe de Lucena Paiva  

---

## 📘 Sobre o Projeto
A **DlupasAPI** é uma API RESTful simples que gerencia produtos, variações, promoções e pedidos utilizando persistência baseada em **arquivos CSV**, conforme orientações da disciplina.

Este sistema simula as operações básicas de gestão de uma loja de camisetas, permitindo:

- Cadastro de produtos  
- Cadastro de variações (cores, tamanhos, preço base, estoque)  
- Associação de promoções  
- Gerenciamento de pedidos (em desenvolvimento)  
- Leitura automática de arquivos CSV  

---

## 🧱 Arquitetura

A arquitetura segue o padrão:

```

controller → service → repository → CSV

```

### **Camadas:**
- **Model:** classes que representam as entidades  
- **Repository:** persistência em CSV  
- **Service:** regras de negócio  
- **Controller:** endpoints REST (Spring Boot)  

---

## 📂 Estrutura de Pastas
```

src/main/java/br/com/dlupas/api
├── controller
│     └── ProdutoController.java
├── service
│     └── ProdutoService.java
├── repository
│     ├── ProdutoRepository.java
│     ├── VariacaoProdutoRepository.java
│     └── PromocaoRepository.java
└── model
├── Produto.java
├── VariacaoProduto.java
├── Promocao.java
├── Pedido.java
└── ItemPedido.java

src/main/resources/data
├── produtos.csv
├── variacoes.csv
└── promocoes.csv

```

---

# 🧩 Entidades Implementadas

### **Produto**
- id  
- nome  
- categoria  
- genero  
- variacoes (lista)  
- promocaoID (lista de IDs)  
- estoqueTotal (calculado a partir das variações)

---

### **VariacaoProduto**
- id  
- produtoId  
- cor  
- tamanho  
- quantidadeEstoque  
- precoBase  

---

### **Promocao**
- id  
- descricao  
- tipo (porcentagem ou combo)  
- valor  
- ativo (booleano)

---

### **Pedido** *(estrutura pronta, pendente implementação de lógica)*
- id  
- data  
- itens  
- totalDescontos  
- valorTotal  
- status  
- cliente  

---

### **ItemPedido** *(estrutura pronta, pendente implementação de lógica)*
- variacaoProdutoId  
- quantidade  
- totalDescontos  
- subtotal  

---

# 💾 Persistência em CSV

Cada entidade possui seu arquivo específico em:

```

src/main/resources/data/

````

E cada repositório é responsável por:

- criar o arquivo se não existir  
- escrever cabeçalho  
- salvar todos os registros  
- inserir, editar e remover entidades  
- buscar por ID  
- buscar por chave estrangeira  

---

# 🧠 Serviços Implementados

### ✔ ProdutoService
- cadastro de produto  
- atualização  
- exclusão + remoção automática das variações associadas  
- listagem completa com variações embutidas  
- cálculo de estoque total  
- listagem de promoções ativas  

---

# 🌐 Controladores Disponíveis

### ✔ ProdutoController — **TOTALMENTE FUNCIONAL**

### 📌 Rotas disponíveis:

---

## 🟦 **Produtos**

### **POST /produtos**
Cadastrar um novo produto.

**Body exemplo:**
```json
{
  "nome": "Camiseta Preta",
  "categoria": "Roupas",
  "genero": "Unissex",
  "promocaoID": []
}
````

---

### **GET /produtos**

Lista todos os produtos com suas variações.

---

### **GET /produtos/{id}**

Retorna um produto completo por ID.

---

### **PUT /produtos/{id}**

Atualiza dados do produto.

---

### **DELETE /produtos/{id}**

Remove o produto e todas as suas variações associadas.

---

### **GET /produtos/{id}/promocoes**

Retorna as promoções ativas vinculadas ao produto.

---

# 🚧 Funcionalidades Pendentes

## 🔹 Implementar Services e Controllers:

### ❗ VariacaoProdutoService

### ❗ VariacaoProdutoController

* cadastrar variação
* editar variação
* deletar variação
* listar por produto
* atualizar estoque
* validar tamanhos e cores

---

### ❗ PromocaoService

### ❗ PromocaoController

* cadastrar promoção
* ativar / desativar ofertas
* aplicar porcentagens nas variações

---

### ❗ PedidoService

### ❗ PedidoController

* criar pedido
* aplicar descontos
* reduzir estoque
* calcular subtotal e total

---

# 🧪 Como Executar o Projeto

1. Certifique-se de ter:

   * JDK 11+
   * Maven
   * Spring Boot

2. Clone o repositório:

```bash
git clone https://github.com/ester-lucena/DlupasAPI
```

3. Entre na pasta do projeto:

```bash
cd DlupasAPI
```

4. Execute:

```bash
mvn spring-boot:run
```

5. Acesse:

```
http://localhost:8080/produtos
```

---

# 🧪 Testando com Insomnia/Postman

### Exemplos de endpoints:

Criar produto:

* POST: `http://localhost:8080/produtos`

Listar:

* GET: `http://localhost:8080/produtos`

Buscar por ID:

* GET: `http://localhost:8080/produtos/{id}`

Atualizar:

* PUT: `http://localhost:8080/produtos/{id}`

Deletar:

* DELETE: `http://localhost:8080/produtos/{id}`

---

# 📜 Licença

Projeto acadêmico — uso livre para fins educacionais.

---

Obs.: Este arquivo foi desenvolvido com auxílio de Inteligência Artificial.
