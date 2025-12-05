package br.com.ronaldo.market_intelligence.domain.service.user;

import br.com.ronaldo.market_intelligence.domain.model.CreateUserResponseModel;
import br.com.ronaldo.market_intelligence.domain.model.DummyUsersResponseModel;
import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.domain.exception.UserExistsException;
import br.com.ronaldo.market_intelligence.domain.exception.UserNotFoundException;
import br.com.ronaldo.market_intelligence.domain.model.CreateUserRequestModel;
import br.com.ronaldo.market_intelligence.domain.repository.UserRepository;
import br.com.ronaldo.market_intelligence.infrastructure.client.DummyJsonClient;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateUserServiceImp implements CreateUserService {
    private static final Logger log = LoggerFactory.getLogger(CreateUserServiceImp.class);

    private final DummyJsonClient dummyJsonClient;
    private final UserMapper mapper;
    private final UserRepository repository;

    public CreateUserServiceImp(DummyJsonClient dummyJsonClient, UserMapper mapper, UserRepository repository) {
        this.dummyJsonClient = dummyJsonClient;
        this.mapper = mapper;
        this.repository = repository;
    }

    public CreateUserResponseModel execute(CreateUserRequestModel createUserRequestModel) {

        if (repository.findByEmail(createUserRequestModel.getEmail()).isPresent()) {
            log.warn("[CreateUserService] - Usuário já cadastrado localmente para o email: {}", createUserRequestModel.getEmail());
            throw new UserExistsException("Usuário já cadastrado localmente para o email: " + createUserRequestModel.getEmail());
        }

        try {
            DummyUsersResponseModel responseModel = dummyJsonClient.searchUserByEmail(createUserRequestModel.getEmail());
            log.info("[UserClient] - CLIENT REQUEST DATA: {}", createUserRequestModel.getEmail());

            if (responseModel.getUsers() == null || responseModel.getUsers().isEmpty()) {

                log.warn("[UserClient] - Nenhum usuário cadastrado no DummyJSON para o email: {}", createUserRequestModel.getEmail());
                throw new UserNotFoundException("Nenhum usuário cadastrado para o email: " + createUserRequestModel.getEmail());
            }

            final var userResponseModel = responseModel.getUsers().getFirst();

            final var userEntity = mapper.toEntity(userResponseModel);
            repository.save(userEntity);

            log.info("[UserEntity] - Usuário salvo no banco local. ID interno: {}", userEntity.getId());

            return userResponseModel;

        } catch (feign.FeignException error) {
            log.error("Erro ao consultar a API DummyJSON: status={} message={}",error.status(), error.getMessage());

            throw new ExternalApiException(
                    "Erro ao consultar a API DummyJSON: " + error.status(), error
            );
        }
    }
}