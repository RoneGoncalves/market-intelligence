package br.com.ronaldo.market_intelligence.domain.service.cart;

import br.com.ronaldo.market_intelligence.domain.adapter.TicketMedioInsightAdapter;
import br.com.ronaldo.market_intelligence.domain.exception.CartsNotFoundException;
import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.domain.adapter.TicketMedioAdapter;
import br.com.ronaldo.market_intelligence.domain.adapter.TicketMedioLocalAdapter;
import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioResponseModel;
import br.com.ronaldo.market_intelligence.infrastructure.cache.DummyCartCache;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.TicketMedioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TicketMedioServiceImp implements TicketMedioService {
    private static final Logger log = LoggerFactory.getLogger(TicketMedioServiceImp.class);

    private final TicketMedioAdapter dummyAdapter;
    private final TicketMedioLocalAdapter localAdapter;
    private final TicketMedioInsightAdapter insightAdapter;
    private final DummyCartCache dummyCartCache;

    public TicketMedioServiceImp(TicketMedioAdapter adapter, TicketMedioLocalAdapter localAdapter, TicketMedioInsightAdapter insightAdapter, TicketMedioMapper mapper, DummyCartCache dummyCartCache) {
        this.dummyAdapter = adapter;
        this.localAdapter = localAdapter;
        this.insightAdapter = insightAdapter;
        this.dummyCartCache = dummyCartCache;
    }

    public TicketMedioResponseModel execute() {

        try {
            log.info("[UserClient] - CLIENT REQUEST Buscando Lista de carts: ");
            CartListModel cartListModel = dummyCartCache.getCarts();

            if (cartListModel.getCarts() == null || cartListModel.getCarts().isEmpty()) {

                log.warn("[UserClient] - Nenhum cart retonado pelo DummyJSON ");
                throw new CartsNotFoundException("Nenhum cart retonado pelo DummyJSON ");
            }

            final var dummyTicketMedio = dummyAdapter.calculaTicketMedio(cartListModel);
            final var ticketMedioLocal = localAdapter.calculaTicketMedioLocal(cartListModel);
            final var ticketMedioInsight = insightAdapter.calculaInsights(dummyTicketMedio, ticketMedioLocal);

            log.info("[TicketMedioService] - Informações de tickets médios processadas...");
            return ticketMedioInsight;

        } catch (feign.FeignException error) {
            log.error("Erro ao consultar a API DummyJSON: status={} message={}",error.status(), error.getMessage());

            throw new ExternalApiException(
                    "Erro ao consultar a API DummyJSON: " + error.status(), error
            );
        }

    }
}
