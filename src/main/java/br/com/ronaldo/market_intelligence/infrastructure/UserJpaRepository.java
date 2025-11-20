package br.com.ronaldo.market_intelligence.infrastructure;

import br.com.ronaldo.market_intelligence.domain.model.UserEntity;
import br.com.ronaldo.market_intelligence.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository
        extends JpaRepository<UserEntity, Long>, UserRepository {
}
