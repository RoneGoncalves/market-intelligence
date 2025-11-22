package br.com.ronaldo.market_intelligence.domain.service.cart;

import br.com.ronaldo.market_intelligence.domain.exception.CartsNotFoundException;
import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.infrastructure.client.DummyJsonClient;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TicketMedioServiceTest {

    @Mock
    private DummyJsonClient dummyJsonClient;

    @InjectMocks
    private TicketMedioService ticketMedioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldThrowCartsNotFoundException_whenCartListIsNull() {

        CartListModel cartListModel = new CartListModel();
        cartListModel.setCarts(null); // lista interna null
        when(dummyJsonClient.getCarts()).thenReturn(cartListModel);

        assertThrows(CartsNotFoundException.class, () -> ticketMedioService.execute());
    }


    @Test
    void execute_shouldThrowCartsNotFoundException_whenCartListIsEmpty() {

        CartListModel emptyCartList = new CartListModel();
        emptyCartList.setCarts(Collections.emptyList()); // lista vazia de verdade
        when(dummyJsonClient.getCarts()).thenReturn(emptyCartList);

        assertThrows(CartsNotFoundException.class, () -> ticketMedioService.execute());
    }


    @Test
    void execute_shouldThrowExternalApiException_whenFeignExceptionOccurs() {

        Request request = Request.create(Request.HttpMethod.GET, "/carts", Collections.emptyMap(), null, new RequestTemplate());
        FeignException feignException = FeignException.errorStatus("getCarts",
                feign.Response.builder()
                        .status(500)
                        .request(request)
                        .headers(Collections.emptyMap())
                        .build()
        );

        when(dummyJsonClient.getCarts()).thenThrow(feignException);

        assertThrows(ExternalApiException.class, () -> ticketMedioService.execute());
    }
}
