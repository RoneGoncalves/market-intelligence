package br.com.ronaldo.market_intelligence.domain.service.user;

import br.com.ronaldo.market_intelligence.application.dto.DummyUsersResponseDto;
import br.com.ronaldo.market_intelligence.application.dto.UserRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.UserResponseDto;
import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.domain.exception.UserExistsException;
import br.com.ronaldo.market_intelligence.domain.exception.UserNotFoundException;
import br.com.ronaldo.market_intelligence.domain.repository.UserRepository;
import br.com.ronaldo.market_intelligence.infrastructure.client.DummyJsonClient;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    private static final Logger log = LoggerFactory.getLogger(CreateUserService.class);

    private final DummyJsonClient dummyJsonClient;
    private final UserMapper mapper;
    private final UserRepository repository;

    public CreateUserService(DummyJsonClient dummyJsonClient, UserMapper mapper, UserRepository repository) {
        this.dummyJsonClient = dummyJsonClient;
        this.mapper = mapper;
        this.repository = repository;
    }

    public UserResponseDto execute(UserRequestDto userRequestDto) {

        if (repository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            log.warn("[CreateUserService] - Usuário já cadastrado localmente para o email: {}", userRequestDto.getEmail());
            throw new UserExistsException("Usuário já cadastrado localmente para o email: " + userRequestDto.getEmail());
        }

        try {
            DummyUsersResponseDto responseDto = dummyJsonClient.searchUserByEmail(userRequestDto.getEmail());
            log.info("[UserClient] - CLIENT REQUEST DATA: {}", userRequestDto.getEmail());

            if (responseDto.getUsers() == null || responseDto.getUsers().isEmpty()) {

                log.warn("[UserClient] - Nenhum usuário cadastrado no DummyJSON para o email: {}", userRequestDto.getEmail());
                throw new UserNotFoundException("Nenhum usuário cadastrado para o email: " + userRequestDto.getEmail());
            }

            UserResponseDto userResponseDto = responseDto.getUsers().getFirst();

            final var userEntity = mapper.toEntity(userResponseDto);
            repository.save(userEntity);

            log.info("[UserEntity] - Usuário salvo no banco local. ID interno: {}", userEntity.getId());

            return userResponseDto;

        } catch (feign.FeignException error) {
            log.error("Erro ao consultar a API DummyJSON: status={} message={}",error.status(), error.getMessage());

            throw new ExternalApiException(
                    "Erro ao consultar a API DummyJSON: " + error.status(), error
            );
        }
    }
}