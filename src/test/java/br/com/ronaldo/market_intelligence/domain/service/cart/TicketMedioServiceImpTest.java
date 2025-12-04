package br.com.ronaldo.market_intelligence.domain.service.cart;

import br.com.ronaldo.market_intelligence.application.dto.TicketMedioResponseDto;
import br.com.ronaldo.market_intelligence.domain.adapter.TicketMedioAdapter;
import br.com.ronaldo.market_intelligence.domain.adapter.TicketMedioInsightAdapter;
import br.com.ronaldo.market_intelligence.domain.adapter.TicketMedioLocalAdapter;
import br.com.ronaldo.market_intelligence.domain.exception.CartsNotFoundException;
import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.domain.model.CartModel;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioDummyModel;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioLocalModel;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioResponseModel;
import br.com.ronaldo.market_intelligence.infrastructure.cache.DummyCartCache;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.TicketMedioMapper;
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

public class TicketMedioServiceImpTest {

    @Mock
    private DummyCartCache cache;

    @Mock
    private TicketMedioAdapter dummyAdapter;

    @Mock
    private TicketMedioLocalAdapter localAdapter;

    @Mock
    private TicketMedioInsightAdapter insightAdapter;

    @Mock
    private TicketMedioMapper mapper;

    @InjectMocks
    private TicketMedioServiceImp service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldThrowCartsNotFoundException_whenCartListIsNull() {
        CartListModel cartListModel = new CartListModel();
        cartListModel.setCarts(null);
        when(cache.getCarts()).thenReturn(cartListModel);
        assertThrows(CartsNotFoundException.class, () -> service.execute());
    }

    @Test
    void execute_shouldThrowCartsNotFoundException_whenCartListIsEmpty() {
        CartListModel emptyCartList = new CartListModel();
        emptyCartList.setCarts(Collections.emptyList());
        when(cache.getCarts()).thenReturn(emptyCartList);
        assertThrows(CartsNotFoundException.class, () -> service.execute());
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

        when(cache.getCarts()).thenThrow(feignException);
        assertThrows(ExternalApiException.class, () -> service.execute());
    }

    @Test
    void execute_shouldReturnDto_whenExecutionIsSuccessful() {
        CartModel cartModel = new CartModel();
        CartListModel cartList = new CartListModel();
        cartList.setCarts(Collections.singletonList(cartModel));

        TicketMedioDummyModel dummyResult = new TicketMedioDummyModel();
        TicketMedioLocalModel localResult = new TicketMedioLocalModel();
        TicketMedioResponseModel responseModel = new TicketMedioResponseModel();
        TicketMedioResponseDto dto = new TicketMedioResponseDto();

        when(cache.getCarts()).thenReturn(cartList);
        when(dummyAdapter.calculaTicketMedio(cartList)).thenReturn(dummyResult);
        when(localAdapter.calculaTicketMedioLocal(cartList)).thenReturn(localResult);
        when(insightAdapter.calculaInsights(dummyResult, localResult)).thenReturn(responseModel);
        when(mapper.toDto(responseModel)).thenReturn(dto);

        service.execute();

        verify(cache, times(1)).getCarts();
        verify(dummyAdapter, times(1)).calculaTicketMedio(cartList);
        verify(localAdapter, times(1)).calculaTicketMedioLocal(cartList);
        verify(insightAdapter, times(1)).calculaInsights(dummyResult, localResult);
    }
}
