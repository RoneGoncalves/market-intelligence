package br.com.ronaldo.market_intelligence.application.controller;
import br.com.ronaldo.market_intelligence.application.dto.DummyUsersResponseDto;
import br.com.ronaldo.market_intelligence.application.dto.UserRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.UserResponseDto;
import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.domain.model.UserEntity;
import br.com.ronaldo.market_intelligence.domain.repository.UserRepository;

import br.com.ronaldo.market_intelligence.infrastructure.client.UserClient;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserRepository repository;

    @MockBean
    private UserClient userClient;

    @MockBean
    private UserMapper mapper;

    @Test
    void shouldCreateUserSuccessfully() throws Exception {

        String email = "john@example.com";

        UserRequestDto request = new UserRequestDto(email);

        UserResponseDto externalUser = new UserResponseDto(
                10L,
                "John",
                "Doe",
                "johndoe",
                email,
                "male",
                45
        );

        DummyUsersResponseDto dummyResponse = new DummyUsersResponseDto();
        dummyResponse.setUsers(Collections.singletonList(externalUser));
        dummyResponse.setTotal(1);
        dummyResponse.setSkip(0);
        dummyResponse.setLimit(1);

        when(repository.findByEmail(email))
                .thenReturn(Optional.empty());

        when(userClient.searchUserByEmail(email))
                .thenReturn(dummyResponse);

        when(mapper.toEntity(externalUser))
                .thenReturn(new UserEntity());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(userClient, times(1)).searchUserByEmail(email);
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldReturn409WhenUserAlreadyExists() throws Exception {

        String email = "john@example.com";
        UserRequestDto request = new UserRequestDto(email);

        when(repository.findByEmail(email))
                .thenReturn(Optional.of(new UserEntity()));

        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn404WhenUserNotFoundInDummyJson() throws Exception {

        String email = "john@example.com";
        UserRequestDto request = new UserRequestDto(email);

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        DummyUsersResponseDto empty = new DummyUsersResponseDto();
        empty.setUsers(Collections.emptyList());
        empty.setTotal(0);
        empty.setSkip(0);
        empty.setLimit(0);

        when(userClient.searchUserByEmail(email)).thenReturn(empty);

        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn502WhenExternalApiFails() throws Exception {

        String email = "john@example.com";
        UserRequestDto request = new UserRequestDto(email);

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        when(userClient.searchUserByEmail(email))
                .thenThrow(new ExternalApiException("dummy fail", null));

        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadGateway());
    }
}


