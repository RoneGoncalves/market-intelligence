package br.com.ronaldo.market_intelligence.domain.repository;

import br.com.ronaldo.market_intelligence.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    void deleteById(Long id);
}
