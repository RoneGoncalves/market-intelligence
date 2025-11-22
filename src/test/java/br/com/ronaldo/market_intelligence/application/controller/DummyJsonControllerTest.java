package br.com.ronaldo.market_intelligence.application.controller;

import br.com.ronaldo.market_intelligence.application.dto.DummyUsersResponseDto;
import br.com.ronaldo.market_intelligence.application.dto.TicketMedioResponseDto;
import br.com.ronaldo.market_intelligence.application.dto.UserRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.UserResponseDto;
import br.com.ronaldo.market_intelligence.domain.entity.UserEntity;
import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.domain.model.CartModel;
import br.com.ronaldo.market_intelligence.domain.repository.UserRepository;

import br.com.ronaldo.market_intelligence.infrastructure.client.DummyJsonClient;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.TicketMedioMapper;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
class DummyJsonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserRepository repository;

    @MockBean
    private DummyJsonClient dummyJsonClient;

    @MockBean
    private UserMapper mapper;

    @MockBean
    private TicketMedioMapper ticketMedioMapper;

    @Test
    void shouldCreateUserSuccessfully() throws Exception {

        String email = "john@example.com";

        UserRequestDto request = new UserRequestDto(email);

        UserResponseDto externalUser = new UserResponseDto(
                10L,
                "John",
                "Doe",
                "johndoe",
                "john@example.com",
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

        when(dummyJsonClient.searchUserByEmail(email))
                .thenReturn(dummyResponse);

        when(mapper.toEntity(externalUser))
                .thenReturn(new UserEntity());

        mockMvc.perform(post("/api/create_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(dummyJsonClient, times(1)).searchUserByEmail(email);
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldReturn409WhenUserAlreadyExists() throws Exception {

        String email = "john@example.com";
        UserRequestDto request = new UserRequestDto(email);

        when(repository.findByEmail(email))
                .thenReturn(Optional.of(new UserEntity()));

        mockMvc.perform(
                        post("/api/create_user")
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

        when(dummyJsonClient.searchUserByEmail(email)).thenReturn(empty);

        mockMvc.perform(
                        post("/api/create_users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn502WhenExternalApiFails() throws Exception {

        String email = "john@example.com";
        UserRequestDto request = new UserRequestDto(email);

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        when(dummyJsonClient.searchUserByEmail(email))
                .thenThrow(new ExternalApiException("dummy fail", null));

        mockMvc.perform(
                        post("/api/create_user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadGateway());
    }

    @Test
    void shouldReturnTicketMedioSuccessfully() throws Exception {

        CartModel cart1 = new CartModel(
                1L,
                100L,
                Collections.emptyList(),
                2,
                4,
                200.0,
                180.0
        );

        CartModel cart2 = new CartModel(
                2L,
                200L,
                Collections.emptyList(),
                1,
                2,
                100.0,
                95.0
        );

        CartListModel externalList = new CartListModel();
        externalList.setCarts(Arrays.asList(cart1, cart2));


        when(dummyJsonClient.getCarts()).thenReturn(externalList);

        TicketMedioResponseDto responseDto = new TicketMedioResponseDto(
                50,
                "R$ 20.085,36",
                "R$ 18.141,28",
                "R$ 160.289,86",
                "R$ 36,28",
                12,
                "R$ 37.754,73",
                "R$ 33.686,79",
                "R$ 160.289,86",
                "R$ 36,28",
                "O ticket médio local é 88,0% maior que o dummy.",
                "O ticket médio descontado local é 85,7% maior que o dummy."
        );

        when(ticketMedioMapper.toDto(any()))
                .thenReturn(responseDto);

        mockMvc.perform(
                        get("/api/ticket_medio")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dummyTotalCart").value(50))
                .andExpect(jsonPath("$.dummyTicketMedio").value("R$ 20.085,36"))
                .andExpect(jsonPath("$.localTotalCart").value(12))
                .andExpect(jsonPath("$.insightTicketMedio").value("O ticket médio local é 88,0% maior que o dummy."));


        // ==== VERIFY =====

        verify(dummyJsonClient, times(1)).getCarts();
    }

    @Test
    void shouldReturn502WhenTicketMedioExternalApiFails() throws Exception {

        when(dummyJsonClient.getCarts())
                .thenThrow(new ExternalApiException("dummy fail", null));

        mockMvc.perform(
                        get("/api/ticket_medio")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadGateway());
    }
}


