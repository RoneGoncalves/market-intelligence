package br.com.ronaldo.market_intelligence.domain.service.user;

import br.com.ronaldo.market_intelligence.domain.exception.UserNotFoundException;
import br.com.ronaldo.market_intelligence.domain.repository.UserRepository;
import br.com.ronaldo.market_intelligence.infrastructure.client.DummyJsonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserService {

    private static final Logger log = LoggerFactory.getLogger(CreateUserService.class);

    private final DummyJsonClient dummyJsonClient;
    private final UserRepository repository;

    public DeleteUserService(DummyJsonClient dummyJsonClient, UserRepository repository) {
        this.dummyJsonClient = dummyJsonClient;
        this.repository = repository;
    }

    public void excluirUsuario(Long id) {

        if(repository.findById(id).isEmpty()) {
            log.warn("[DeleteUserService] - Nenhum usuário local cadastrado com o id: {}", id);
            throw new UserNotFoundException("Nenhum usuário local cadastrado com o id:" + id);
        }
        repository.deleteById(id);
        log.warn("[DeleteUserService] - Usuario excluido par o id: {}", id);
    }
}
