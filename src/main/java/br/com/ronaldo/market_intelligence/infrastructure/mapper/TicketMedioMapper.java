package br.com.ronaldo.market_intelligence.infrastructure.mapper;

import br.com.ronaldo.market_intelligence.application.dto.TicketMedioResponseDto;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioResponseModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMedioMapper {

    TicketMedioResponseDto toDto(TicketMedioResponseModel ticketMedioResponseModel);
}
