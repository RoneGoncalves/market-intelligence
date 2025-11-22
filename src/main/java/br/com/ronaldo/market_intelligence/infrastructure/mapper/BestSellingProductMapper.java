package br.com.ronaldo.market_intelligence.infrastructure.mapper;

import br.com.ronaldo.market_intelligence.application.dto.BestSellingProductDto;
import br.com.ronaldo.market_intelligence.domain.model.BestSellingProductModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BestSellingProductMapper {

    BestSellingProductDto toDto(BestSellingProductModel bestSellingProductModel);
}
