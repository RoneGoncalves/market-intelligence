package br.com.ronaldo.market_intelligence.domain.service.cart;

import br.com.ronaldo.market_intelligence.application.dto.TicketMedioResponseDto;
import br.com.ronaldo.market_intelligence.domain.adapter.TicketMedioInsightAdapter;
import br.com.ronaldo.market_intelligence.domain.exception.CartsNotFoundException;
import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.domain.adapter.TicketMedioAdapter;
import br.com.ronaldo.market_intelligence.domain.adapter.TicketMedioLocalAdapter;
import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.infrastructure.client.DummyJsonClient;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.TicketMedioMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TicketMedioService {
    private static final Logger log = LoggerFactory.getLogger(TicketMedioService.class);
    private ObjectMapper objectMapper;

    private final DummyJsonClient dummyJsonClient;
    private final TicketMedioAdapter dummyAdapter;
    private final TicketMedioLocalAdapter localAdapter;
    private final TicketMedioInsightAdapter insightAdapter;
    private final TicketMedioMapper mapper;

    public TicketMedioService(DummyJsonClient dummyJsonClient, TicketMedioAdapter adapter, TicketMedioLocalAdapter localAdapter, TicketMedioInsightAdapter insightAdapter, TicketMedioMapper mapper) {
        this.dummyJsonClient = dummyJsonClient;
        this.dummyAdapter = adapter;
        this.localAdapter = localAdapter;
        this.insightAdapter = insightAdapter;
        this.mapper = mapper;
    }

    public TicketMedioResponseDto execute() {

        try {
            log.info("[UserClient] - CLIENT REQUEST Buscando Lista de carts: ");
            CartListModel cartListModel = dummyJsonClient.getCarts();

            if (cartListModel.getCarts() == null || cartListModel.getCarts().isEmpty()) {

                log.warn("[UserClient] - Nenhum cart retonado pelo DummyJSON ");
                throw new CartsNotFoundException("Nenhum cart retonado pelo DummyJSON ");
            }

            final var dummyTicketMedio = dummyAdapter.calculaTicketMedio(cartListModel);
            final var ticketMedioLocal = localAdapter.calculaTicketMedioLocal(cartListModel);
            final var ticketMedioInsight = insightAdapter.calculaInsights(dummyTicketMedio, ticketMedioLocal);

            log.info("[TicketMedioService] - Informações de tickets médios processadas...");
            return mapper.toDto(ticketMedioInsight);

        } catch (feign.FeignException error) {
            log.error("Erro ao consultar a API DummyJSON: status={} message={}",error.status(), error.getMessage());

            throw new ExternalApiException(
                    "Erro ao consultar a API DummyJSON: " + error.status(), error
            );
        }

    }
}
