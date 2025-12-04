package br.com.ronaldo.market_intelligence.application.controller;

import br.com.ronaldo.market_intelligence.application.dto.BestSellingProductDto;
import br.com.ronaldo.market_intelligence.application.dto.UserRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.UserResponseDto;
import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioResponseModel;
import br.com.ronaldo.market_intelligence.domain.service.cart.TicketMedioServiceImp;
import br.com.ronaldo.market_intelligence.domain.service.product.BestSellingProductServiceImp;
import br.com.ronaldo.market_intelligence.domain.service.user.CreateUserServiceImp;
import br.com.ronaldo.market_intelligence.domain.service.user.DeleteUserServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DummyJsonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateUserServiceImp createUserServiceImp;

    @MockBean
    private DeleteUserServiceImp deleteUserServiceImp;

    @MockBean
    private TicketMedioServiceImp ticketMedioServiceImp;

    @MockBean
    private BestSellingProductServiceImp productService;

    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        String email = "john@example.com";
        UserRequestDto request = new UserRequestDto(email);
        UserResponseDto response = new UserResponseDto(
                10L, "John", "Doe", "johndoe", email, "male", 45
        );

        when(createUserServiceImp.execute(any())).thenReturn(response);

        mockMvc.perform(post("/api/create_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(createUserServiceImp, times(1)).execute(any());
    }

    @Test
    void shouldReturn409WhenUserAlreadyExists() throws Exception {
        String email = "john@example.com";
        UserRequestDto request = new UserRequestDto(email);

        when(createUserServiceImp.execute(any()))
                .thenThrow(new ExternalApiException("User exists", null));

        mockMvc.perform(post("/api/create_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadGateway());
    }

    @Test
    void shouldReturn502WhenExternalApiFails() throws Exception {
        String email = "john@example.com";
        UserRequestDto request = new UserRequestDto(email);

        when(createUserServiceImp.execute(any()))
                .thenThrow(new ExternalApiException("dummy fail", null));

        mockMvc.perform(post("/api/create_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadGateway());
    }

    @Test
    void shouldReturnTicketMedioSuccessfully() throws Exception {
        TicketMedioResponseModel response = new TicketMedioResponseModel(
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
                "Insight 1",
                "Insight 2"
        );

        when(ticketMedioServiceImp.execute()).thenReturn(response);

        mockMvc.perform(get("/api/ticket_medio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dummyTotalCart").value(50))
                .andExpect(jsonPath("$.dummyTicketMedio").value("R$ 20.085,36"))
                .andExpect(jsonPath("$.localTotalCart").value(12));

        verify(ticketMedioServiceImp, times(1)).execute();
    }

    @Test
    void shouldReturn502WhenTicketMedioExternalApiFails() throws Exception {
        when(ticketMedioServiceImp.execute())
                .thenThrow(new ExternalApiException("dummy fail", null));

        mockMvc.perform(get("/api/ticket_medio"))
                .andExpect(status().isBadGateway());
    }

    @Test
    void shouldDeleteUserSuccessfully() throws Exception {
        Long id = 10L;

        doNothing().when(deleteUserServiceImp).excluirUsuario(id);

        mockMvc.perform(delete("/api/delete_user/{id}", id))
                .andExpect(status().isNoContent());

        verify(deleteUserServiceImp, times(1)).excluirUsuario(id);
    }

    @Test
    void shouldReturn404WhenDeletingNonExistingUser() throws Exception {
        Long id = 999L;

        doNothing().when(deleteUserServiceImp).excluirUsuario(id);

        mockMvc.perform(delete("/api/delete_user/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnBestSellingProductSuccessfully() throws Exception {
        BestSellingProductDto dto = new BestSellingProductDto(
                "Produto A", 15,
                "Produto B", 2
        );

        when(productService.execute()).thenReturn(dto);

        mockMvc.perform(get("/api/product_selling_analyses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produtoMaisVendido").value("Produto A"))
                .andExpect(jsonPath("$.qtdProdutoMaisVendido").value(15))
                .andExpect(jsonPath("$.produtoMenosVendido").value("Produto B"))
                .andExpect(jsonPath("$.qtdProdutoMenosVendido").value(2));

        verify(productService).execute();
    }

    @Test
    void shouldReturn502WhenBestSellingProductApiFails() throws Exception {
        when(productService.execute())
                .thenThrow(new ExternalApiException("Erro DummyJSON", null));

        mockMvc.perform(get("/api/product_selling_analyses"))
                .andExpect(status().isBadGateway());
    }
}
