package br.com.ronaldo.market_intelligence.domain.service.user;

import br.com.ronaldo.market_intelligence.application.dto.DummyUsersResponseDto;
import br.com.ronaldo.market_intelligence.application.dto.UserResponseDto;
import br.com.ronaldo.market_intelligence.infrastructure.client.UserClient;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserClient userClient;
    private final UserDomainService domainService;
    private final UserMapper mapper;

    public UserResponseDto execute(String email) {

        DummyUsersResponseDto responseDto = userClient.searchUserByEmail(email);
        UserResponseDto userResponseDto = responseDto.getUsers().getFirst();
        var userMapped = mapper.toEntity(userResponseDto);

        domainService.salvar(userMapped);

        return userResponseDto;
    }
}