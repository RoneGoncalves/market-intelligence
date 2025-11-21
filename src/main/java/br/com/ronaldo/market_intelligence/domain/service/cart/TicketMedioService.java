package br.com.ronaldo.market_intelligence.domain.service.cart;

import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioLocalModel;
import br.com.ronaldo.market_intelligence.domain.adapter.TicketMedioAdapter;
import br.com.ronaldo.market_intelligence.domain.adapter.TicketMedioLocalAdapter;
import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.domain.service.user.CreateUserService;
import br.com.ronaldo.market_intelligence.infrastructure.client.DummyJsonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TicketMedioService {
    private static final Logger log = LoggerFactory.getLogger(CreateUserService.class);

    private final DummyJsonClient dummyJsonClient;
    private final TicketMedioAdapter dummyAdapter;
    private final TicketMedioLocalAdapter localAdapter;

    public TicketMedioService(DummyJsonClient dummyJsonClient, TicketMedioAdapter adapter, TicketMedioLocalAdapter localAdapter) {
        this.dummyJsonClient = dummyJsonClient;
        this.dummyAdapter = adapter;
        this.localAdapter = localAdapter;
    }

    public TicketMedioLocalModel execute() {

        try {

            CartListModel cartListModel = dummyJsonClient.getCarts();

            var dummyTicketMedio = dummyAdapter.calculaTicketMedio(cartListModel);
            var ticketMedioLocal = localAdapter.calculaTicketMedioLocal(cartListModel);

            return ticketMedioLocal;

        } catch (feign.FeignException error) {
            log.error("Erro ao consultar a API DummyJSON: status={} message={}",error.status(), error.getMessage());

            throw new ExternalApiException(
                    "Erro ao consultar a API DummyJSON: " + error.status(), error
            );
        }

    }
}
