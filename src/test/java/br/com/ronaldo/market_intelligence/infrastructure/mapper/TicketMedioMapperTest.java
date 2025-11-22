package br.com.ronaldo.market_intelligence.infrastructure.mapper;

import br.com.ronaldo.market_intelligence.application.dto.TicketMedioResponseDto;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class TicketMedioMapperTest {

    private TicketMedioMapper mapper;

    @BeforeEach
    void setup() {
        mapper = Mappers.getMapper(TicketMedioMapper.class);
    }

    @Test
    void shouldMapModelToDtoCorrectly() {
        TicketMedioResponseModel model = getTicketMedioResponseModel();

        TicketMedioResponseDto dto = mapper.toDto(model);

        assertNotNull(dto);

        assertEquals(model.getDummyTotalCart(), dto.getDummyTotalCart());
        assertEquals(model.getDummyTicketMedio(), dto.getDummyTicketMedio());
        assertEquals(model.getDummyTicketMedioComDesconto(), dto.getDummyTicketMedioComDesconto());
        assertEquals(model.getDummyMaiorTicket(), dto.getDummyMaiorTicket());
        assertEquals(model.getDummyMenorTicket(), dto.getDummyMenorTicket());

        assertEquals(model.getLocalTotalCart(), dto.getLocalTotalCart());
        assertEquals(model.getLocalTicketMedio(), dto.getLocalTicketMedio());
        assertEquals(model.getLocalTicketMedioDescontado(), dto.getLocalTicketMedioDescontado());
        assertEquals(model.getLocalMaiorTicket(), dto.getLocalMaiorTicket());
        assertEquals(model.getLocalMenorTicket(), dto.getLocalMenorTicket());

        assertEquals(model.getInsightTicketMedio(), dto.getInsightTicketMedio());
        assertEquals(model.getInsightTicketMedioDescontado(), dto.getInsightTicketMedioDescontado());
    }

    private static TicketMedioResponseModel getTicketMedioResponseModel() {
        TicketMedioResponseModel model = new TicketMedioResponseModel();
        model.setDummyTotalCart(10);
        model.setDummyTicketMedio("100.50");
        model.setDummyTicketMedioComDesconto("90.00");
        model.setDummyMaiorTicket("300.00");
        model.setDummyMenorTicket("10.00");
        model.setLocalTotalCart(5);
        model.setLocalTicketMedio("120.00");
        model.setLocalTicketMedioDescontado("110.00");
        model.setLocalMaiorTicket("200.00");
        model.setLocalMenorTicket("15.00");
        model.setInsightTicketMedio("Médio de compra acima da média");
        model.setInsightTicketMedioDescontado("Clientes utilizam desconto com frequência");
        return model;
    }

    @Test
    void shouldHandleNullFieldsSafely() {
        TicketMedioResponseModel model = new TicketMedioResponseModel();

        TicketMedioResponseDto dto = mapper.toDto(model);

        assertNotNull(dto);

        assertNull(dto.getDummyTotalCart());
        assertNull(dto.getDummyTicketMedio());
        assertNull(dto.getDummyTicketMedioComDesconto());
        assertNull(dto.getDummyMaiorTicket());
        assertNull(dto.getDummyMenorTicket());

        assertNull(dto.getLocalTotalCart());
        assertNull(dto.getLocalTicketMedio());
        assertNull(dto.getLocalTicketMedioDescontado());
        assertNull(dto.getLocalMaiorTicket());
        assertNull(dto.getLocalMenorTicket());

        assertNull(dto.getInsightTicketMedio());
        assertNull(dto.getInsightTicketMedioDescontado());
    }

    @Test
    void shouldReturnNullWhenInputIsNull() {
        TicketMedioResponseDto dto = mapper.toDto(null);
        assertNull(dto);
    }
}
