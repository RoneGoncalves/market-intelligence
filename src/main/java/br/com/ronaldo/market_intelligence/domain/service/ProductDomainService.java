package br.com.ronaldo.market_intelligence.domain.service;

import br.com.ronaldo.market_intelligence.domain.model.Product;
import br.com.ronaldo.market_intelligence.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDomainService {

    private final ProductRepository repository;

    public ProductDomainService(ProductRepository repository) {
        this.repository = repository;
    }

    // -----------------------------
    // CRUD + REGRAS DE NEGÓCIO
    // -----------------------------

    public Product salvar(Product product) {
        validarProduto(product);
        return repository.save(product);
    }

    public Product buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado. ID: " + id)
                );
    }

    public List<Product> listarTodos() {
        return repository.findAll();
    }

    public Product atualizar(Long id, Product dadosAtualizados) {
        Product existente = buscarPorId(id);

        validarProduto(dadosAtualizados);

        existente.setNome(dadosAtualizados.getNome());
        existente.setDescricao(dadosAtualizados.getDescricao());
        existente.setPreco(dadosAtualizados.getPreco());
        existente.setCategoria(dadosAtualizados.getCategoria());
        existente.setOrigem(dadosAtualizados.getOrigem());

        return repository.save(existente);
    }

    public void excluir(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new RuntimeException("Produto não encontrado para exclusão. ID: " + id);
        }

        repository.deleteById(id);
    }

    // -----------------------------
    // REGRAS DE NEGÓCIO DO DOMÍNIO
    // -----------------------------

    private void validarProduto(Product p) {

        if (p.getNome() == null || p.getNome().isBlank()) {
            throw new RuntimeException("Nome do produto é obrigatório");
        }

        if (p.getPreco() == null || p.getPreco() <= 0) {
            throw new RuntimeException("Preço deve ser maior que zero");
        }

        if (p.getCategoria() == null) {
            throw new RuntimeException("Categoria é obrigatório");
        }

        if (p.getOrigem() == null) {
            throw new RuntimeException("Origem é obrigatório");
        }
    }
}

