package br.com.ronaldo.market_intelligence.infrastructure;

import br.com.ronaldo.market_intelligence.domain.model.Product;
import br.com.ronaldo.market_intelligence.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository
        extends JpaRepository<Product, Long>, ProductRepository {
}
