package br.com.ronaldo.market_intelligence.application.controller;

import br.com.ronaldo.market_intelligence.application.dto.ProductRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.ProductResponseDto;
import br.com.ronaldo.market_intelligence.domain.service.product.CreateProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private CreateProductService createProductService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarProdutoERetornarResponseEntityOk() {

        var request = ProductRequestDto.builder()
                .nome("Galaxy S23")
                .descricao("Smartphone Samsung")
                .preco(5999.99)
                .categoria("Celular")
                .origem("Coreia do Sul")
                .build();

        var response = ProductResponseDto.builder()
                .id(1L)
                .nome("Galaxy S23")
                .descricao("Smartphone Samsung")
                .preco(5999.99)
                .categoria("Celular")
                .origem("Coreia do Sul")
                .build();

        when(createProductService.execute(request)).thenReturn(response);

        ResponseEntity<ProductResponseDto> result = productController.criar(request);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(response);
        assertThat(result.getBody().getCategoria()).isEqualTo("Celular");

        verify(createProductService, times(1)).execute(request);
        verifyNoMoreInteractions(createProductService);
    }
}
