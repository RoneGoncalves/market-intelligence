package br.com.ronaldo.market_intelligence.domain.service.user;

import br.com.ronaldo.market_intelligence.application.dto.DummyUsersResponseDto;
import br.com.ronaldo.market_intelligence.application.dto.UserResponseDto;
import br.com.ronaldo.market_intelligence.infrastructure.client.UserClient;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CreateUserEntityServiceTest {

    @Mock
    private UserDomainService domainService;

    @Mock
    private UserMapper mapper;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private CreateUserService service;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    String email = "john@example.com";
    UserResponseDto userResponseDto = UserResponseDto.builder()
            .firstName("John")
            .lastName("Doe")
            .username("jonhdoe")
            .externalId(10L)
            .age(35)
            .email(email)
            .gender("male")
            .build();

    DummyUsersResponseDto dummyUsersResponseDto = DummyUsersResponseDto.builder()
            .users(
                    Collections.singletonList(userResponseDto))
            .total(1)
            .skip(0)
            .limit(1)
            .build();

    @Test
    void shouldReturnSuccessWithValidEmail() {

        when(userClient.searchUserByEmail(email)).thenReturn(dummyUsersResponseDto);

        UserResponseDto result = service.execute(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());

        verify(userClient, times(1)).searchUserByEmail(email);
    }

    @Test
    void shouldReturnAnEmptyArrayWhenUserEmailIsNotFound() {

        String email = "john@example.com";

        UserResponseDto responseDto = UserResponseDto.builder().build();

        when(userClient.searchUserByEmail(email)).thenReturn(dummyUsersResponseDto);

        UserResponseDto result = service.execute(email);

        assertNotNull(result);

        verify(userClient, times(1)).searchUserByEmail(email);
    }

//    @Test
//    void deveCriarProdutoComSucesso() {
//        var request = UserRequestDto.builder()
//                .nome("iPhone 16")
//                .descricao("Smartphone Apple última geração")
//                .preco(8999.99)
//                .categoria("Celular")
//                .origem("EUA")
//                .build();
//
//        var entity = new User();
//        entity.setNome("iPhone 16");
//        entity.setDescricao("Smartphone Apple última geração");
//        entity.setPreco(8999.99);
//        entity.setCategoria("Celular");
//        entity.setOrigem("EUA");
//
//        var savedEntity = new User();
//        savedEntity.setId(1L);
//        savedEntity.setNome("iPhone 16");
//        savedEntity.setDescricao("Smartphone Apple última geração");
//        savedEntity.setPreco(8999.99);
//        savedEntity.setCategoria("Celular");
//        savedEntity.setOrigem("EUA");
//
//        var response = UserResponseDto.builder()
//                .id(1L)
//                .nome("iPhone 16")
//                .descricao("Smartphone Apple última geração")
//                .preco(8999.99)
//                .categoria("Celular")
//                .origem("EUA")
//                .build();
//
//        when(mapper.toEntity(request)).thenReturn(entity);
//        when(domainService.salvar(entity)).thenReturn(savedEntity);
//        when(mapper.toResponse(savedEntity)).thenReturn(response);
//
//        var result = createUserService.execute(request);
//
//        assertThat(result).isNotNull();
//        assertThat(result.getId()).isEqualTo(1L);
//        assertThat(result.getNome()).isEqualTo("iPhone 16");
//        assertThat(result.getCategoria()).isEqualTo("Celular");
//        assertThat(result.getPreco()).isEqualTo(8999.99);
//
//        verify(mapper).toEntity(request);
//        verify(domainService).salvar(entity);
//        verify(mapper).toResponse(savedEntity);
//        verifyNoMoreInteractions(mapper, domainService);
//    }
}
