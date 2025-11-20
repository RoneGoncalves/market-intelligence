package br.com.ronaldo.market_intelligence.domain.repository;

import br.com.ronaldo.market_intelligence.domain.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<UserEntity> findAll();
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByEmail(String email);
    UserEntity save(UserEntity user);
    void deleteById(Long id);
}
