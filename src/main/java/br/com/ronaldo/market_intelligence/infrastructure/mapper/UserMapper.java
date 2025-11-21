package br.com.ronaldo.market_intelligence.infrastructure.mapper;

import br.com.ronaldo.market_intelligence.application.dto.UserResponseDto;
import br.com.ronaldo.market_intelligence.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(UserResponseDto responseDto);
}
