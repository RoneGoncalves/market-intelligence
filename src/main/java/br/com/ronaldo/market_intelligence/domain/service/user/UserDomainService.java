package br.com.ronaldo.market_intelligence.domain.service.user;

import br.com.ronaldo.market_intelligence.domain.model.UserEntity;
import br.com.ronaldo.market_intelligence.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDomainService {

    private final UserRepository repository;

    public UserDomainService(UserRepository repository) {
        this.repository = repository;
    }

    public UserEntity salvar(UserEntity user) {
        return repository.save(user);
    }

    public UserEntity buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado. ID: " + id)
                );
    }

    public boolean buscarPorEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    public List<UserEntity> listarTodos() {
        return repository.findAll();
    }

    public void excluir(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new RuntimeException("Usuário não encontrado para exclusão. ID: " + id);
        }

        repository.deleteById(id);
    }
}

