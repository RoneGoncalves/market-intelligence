package br.com.ronaldo.market_intelligence.domain.service.product;

import br.com.ronaldo.market_intelligence.application.dto.BestSellingProductDto;
import br.com.ronaldo.market_intelligence.domain.adapter.BestSellingProductAdapter;
import br.com.ronaldo.market_intelligence.domain.exception.CartsNotFoundException;
import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.domain.model.BestSellingProductModel;
import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.domain.model.CartModel;
import br.com.ronaldo.market_intelligence.domain.model.ProductModel;
import br.com.ronaldo.market_intelligence.infrastructure.cache.DummyCartCache;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.BestSellingProductMapper;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BestSellingProductServiceImpTest {

    @Mock
    private BestSellingProductAdapter adapter;

    @Mock
    private BestSellingProductMapper mapper;

    @Mock
    private DummyCartCache cache;

    @InjectMocks
    private BestSellingProductServiceImp service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldThrowCartsNotFoundException_whenCartListIsNull() {
        CartListModel model = new CartListModel();
        model.setCarts(null);
        when(cache.getCarts()).thenReturn(model);
        assertThrows(CartsNotFoundException.class, () -> service.execute());
    }

    @Test
    void execute_shouldThrowCartsNotFoundException_whenCartListIsEmpty() {
        CartListModel model = new CartListModel();
        model.setCarts(Collections.emptyList());
        when(cache.getCarts()).thenReturn(model);
        assertThrows(CartsNotFoundException.class, () -> service.execute());
    }

    @Test
    void execute_shouldReturnDto_whenValidCartList() {
        CartModel cart = new CartModel();
        ProductModel product = new ProductModel();
        product.setTitle("Mouse");
        product.setQuantity(5);
        cart.setProducts(List.of(product));

        CartListModel cartList = new CartListModel();
        cartList.setCarts(List.of(cart));

        BestSellingProductModel model = new BestSellingProductModel();
        model.setProdutoMaisVendido("Mouse");
        model.setQtdProdutoMaisVendido(5);

        BestSellingProductDto dto = new BestSellingProductDto();
        dto.setProdutoMaisVendido("Mouse");
        dto.setQtdProdutoMaisVendido(5);

        when(cache.getCarts()).thenReturn(cartList);
        when(adapter.calculaProdutoMaisVendidoDummy(cartList)).thenReturn(model);
        when(mapper.toDto(model)).thenReturn(dto);

        BestSellingProductDto result = service.execute();

        assertNotNull(result);
        assertEquals("Mouse", result.getProdutoMaisVendido());
        verify(cache, times(1)).getCarts();
        verify(adapter, times(1)).calculaProdutoMaisVendidoDummy(cartList);
        verify(mapper, times(1)).toDto(model);
    }

    @Test
    void execute_shouldThrowExternalApiException_whenFeignExceptionOccurs() {
        Request request = Request.create(Request.HttpMethod.GET, "/carts",
                Collections.emptyMap(), null, new RequestTemplate());

        FeignException feignException = FeignException.errorStatus(
                "getCarts",
                feign.Response.builder()
                        .status(500)
                        .request(request)
                        .build()
        );

        when(cache.getCarts()).thenThrow(feignException);

        assertThrows(ExternalApiException.class, () -> service.execute());
    }
}
