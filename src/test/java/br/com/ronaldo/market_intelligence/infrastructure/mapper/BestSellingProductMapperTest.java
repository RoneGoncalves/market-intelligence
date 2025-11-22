package br.com.ronaldo.market_intelligence.infrastructure.mapper;

import br.com.ronaldo.market_intelligence.application.dto.BestSellingProductDto;
import br.com.ronaldo.market_intelligence.domain.entity.UserEntity;
import br.com.ronaldo.market_intelligence.domain.model.BestSellingProductModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class BestSellingProductMapperTest {

    private final BestSellingProductMapper mapper = Mappers.getMapper(BestSellingProductMapper.class);

    @Test
    void shouldMapModelToDto() {

        BestSellingProductModel model = new BestSellingProductModel(
                "Produto A", 10,
                "Produto B", 2
        );

        BestSellingProductDto dto = mapper.toDto(model);

        assertNotNull(dto);
        assertEquals(model.getProdutoMaisVendido(), dto.getProdutoMaisVendido());
        assertEquals(model.getQtdProdutoMaisVendido(), dto.getQtdProdutoMaisVendido());
        assertEquals(model.getProdutoMenosVendido(), dto.getProdutoMenosVendido());
        assertEquals(model.getQtdProdutoMenosVendido(), dto.getQtdProdutoMenosVendido());
    }

    @Test
    void shouldHandleNullInput() {
        BestSellingProductDto bestSellingProductDto = mapper.toDto(null);
        assertNull(bestSellingProductDto);
    }
}
