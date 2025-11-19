package br.com.ronaldo.market_intelligence.infrastructure.mapper;

import br.com.ronaldo.market_intelligence.application.dto.ProductRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.ProductResponseDto;
import br.com.ronaldo.market_intelligence.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductRequestDto request);

    ProductResponseDto toResponse(Product entity);
}
