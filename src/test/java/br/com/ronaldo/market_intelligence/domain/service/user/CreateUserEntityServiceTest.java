package br.com.ronaldo.market_intelligence.domain.service.user;

import br.com.ronaldo.market_intelligence.domain.model.DummyUsersResponseModel;
import br.com.ronaldo.market_intelligence.application.dto.UserRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.UserResponseDto;
import br.com.ronaldo.market_intelligence.domain.entity.UserEntity;
import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.domain.exception.UserExistsException;
import br.com.ronaldo.market_intelligence.domain.exception.UserNotFoundException;
import br.com.ronaldo.market_intelligence.domain.repository.UserRepository;
import br.com.ronaldo.market_intelligence.infrastructure.client.DummyJsonClient;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.UserMapper;
import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateUserEntityServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private DummyJsonClient dummyJsonClient;

    @InjectMocks
    private CreateUserService service;

    private final String email = "john@example.com";

    private UserResponseDto userResponseDto;
    private DummyUsersResponseModel dummyUsersResponseModel;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        userResponseDto = new UserResponseDto();
        userResponseDto.setFirstName("John");
        userResponseDto.setLastName("Doe");
        userResponseDto.setUsername("jonhdoe");
        userResponseDto.setExternalId(10L);
        userResponseDto.setAge(35);
        userResponseDto.setEmail(email);
        userResponseDto.setGender("male");


        dummyUsersResponseModel = new DummyUsersResponseModel();
        dummyUsersResponseModel.setUsers(Collections.singletonList(userResponseDto));
        dummyUsersResponseModel.setTotal(1);
        dummyUsersResponseModel.setSkip(0);
        dummyUsersResponseModel.setLimit(1);

    }

    @Test
    void shouldReturnSuccessWithValidEmail() {

        UserRequestDto request = new UserRequestDto(email);

        when(repository.findByEmail(email)).thenReturn(Optional.empty());
        when(dummyJsonClient.searchUserByEmail(email)).thenReturn(dummyUsersResponseModel);
        when(mapper.toEntity(userResponseDto)).thenReturn(new UserEntity());

        UserResponseDto result = service.execute(request);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals("John", result.getFirstName());

        verify(dummyJsonClient, times(1)).searchUserByEmail(email);
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {

        UserRequestDto request = new UserRequestDto(email);

        when(repository.findByEmail(email))
                .thenReturn(Optional.of(new UserEntity()));

        assertThrows(UserExistsException.class, () -> service.execute(request));

        verify(dummyJsonClient, never()).searchUserByEmail(any());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundInExternalApi() {

        UserRequestDto request = new UserRequestDto(email);

        when(repository.findByEmail(email)).thenReturn(Optional.empty());
        when(dummyJsonClient.searchUserByEmail(email))
                .thenReturn(new DummyUsersResponseModel(Collections.emptyList(), 0, 0, 0));

        assertThrows(UserNotFoundException.class, () -> service.execute(request));

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExternalApiExceptionWhenFeignFails() {

        UserRequestDto request = new UserRequestDto(email);

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        Request req = Request.create(
                Request.HttpMethod.GET,
                "/users/search",
                Collections.emptyMap(),
                null,
                StandardCharsets.UTF_8
        );

        FeignException fakeException =
                FeignException.errorStatus(
                        "searchUserByEmail",
                        Response.builder()
                                .status(500)
                                .reason("Internal Server Error")
                                .request(req)
                                .headers(Collections.emptyMap())
                                .build()
                );

        when(dummyJsonClient.searchUserByEmail(email)).thenThrow(fakeException);

        assertThrows(ExternalApiException.class, () -> service.execute(request));

        verify(repository, never()).save(any());
    }
}
