package br.com.ronaldo.market_intelligence.domain.service.product;

import br.com.ronaldo.market_intelligence.application.dto.ProductRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.ProductResponseDto;
import br.com.ronaldo.market_intelligence.domain.model.Product;
import br.com.ronaldo.market_intelligence.domain.service.ProductDomainService;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateProductServiceTest {

    @Mock
    private ProductDomainService domainService;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private CreateProductService createProductService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarProdutoComSucesso() {
        var request = ProductRequestDto.builder()
                .nome("iPhone 16")
                .descricao("Smartphone Apple última geração")
                .preco(8999.99)
                .categoria("Celular")
                .origem("EUA")
                .build();

        var entity = new Product();
        entity.setNome("iPhone 16");
        entity.setDescricao("Smartphone Apple última geração");
        entity.setPreco(8999.99);
        entity.setCategoria("Celular");
        entity.setOrigem("EUA");

        var savedEntity = new Product();
        savedEntity.setId(1L);
        savedEntity.setNome("iPhone 16");
        savedEntity.setDescricao("Smartphone Apple última geração");
        savedEntity.setPreco(8999.99);
        savedEntity.setCategoria("Celular");
        savedEntity.setOrigem("EUA");

        var response = ProductResponseDto.builder()
                .id(1L)
                .nome("iPhone 16")
                .descricao("Smartphone Apple última geração")
                .preco(8999.99)
                .categoria("Celular")
                .origem("EUA")
                .build();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(domainService.salvar(entity)).thenReturn(savedEntity);
        when(mapper.toResponse(savedEntity)).thenReturn(response);

        var result = createProductService.execute(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNome()).isEqualTo("iPhone 16");
        assertThat(result.getCategoria()).isEqualTo("Celular");
        assertThat(result.getPreco()).isEqualTo(8999.99);

        verify(mapper).toEntity(request);
        verify(domainService).salvar(entity);
        verify(mapper).toResponse(savedEntity);
        verifyNoMoreInteractions(mapper, domainService);
    }
}
