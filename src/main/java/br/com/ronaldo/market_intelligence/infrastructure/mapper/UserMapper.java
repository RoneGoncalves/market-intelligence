package br.com.ronaldo.market_intelligence.infrastructure.mapper;

import br.com.ronaldo.market_intelligence.application.dto.CreateUserRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.CreateUserResponseDto;
import br.com.ronaldo.market_intelligence.domain.entity.UserEntity;
import br.com.ronaldo.market_intelligence.domain.model.CreateUserRequestModel;
import br.com.ronaldo.market_intelligence.domain.model.CreateUserResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(CreateUserResponseModel responseModel);

    CreateUserResponseDto toDto(CreateUserResponseModel responseModel);

    CreateUserRequestModel toModel(CreateUserRequestDto requestDto);
}
