package br.com.ronaldo.market_intelligence.infrastructure.cache;

import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.infrastructure.client.DummyJsonClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DummyCartCacheTest {

    private DummyJsonClient dummyJsonClient;
    private DummyCartCache dummyCartCache;

    @BeforeEach
    void setup() {
        dummyJsonClient = mock(DummyJsonClient.class);
        dummyCartCache = new DummyCartCache(dummyJsonClient);
    }

    @Test
    void shouldReturnFreshDataWhenCacheIsEmpty() {
        CartListModel apiResponse = new CartListModel();
        when(dummyJsonClient.getCarts()).thenReturn(apiResponse);

        CartListModel result = dummyCartCache.getCarts();

        assertNotNull(result);
        assertEquals(apiResponse, result);

        verify(dummyJsonClient, times(1)).getCarts();
    }

    @Test
    void shouldReturnCachedDataWhenCacheIsValid() {

        CartListModel apiResponse = new CartListModel();
        when(dummyJsonClient.getCarts()).thenReturn(apiResponse);

        dummyCartCache.getCarts();

        CartListModel result2 = dummyCartCache.getCarts();

        assertEquals(apiResponse, result2);

        verify(dummyJsonClient, times(1)).getCarts();
    }

    @Test
    void shouldRefreshCacheWhenCacheExpires() throws Exception {

        CartListModel firstResponse = new CartListModel();
        CartListModel secondResponse = new CartListModel();

        when(dummyJsonClient.getCarts())
                .thenReturn(firstResponse)
                .thenReturn(secondResponse);

        dummyCartCache.getCarts();

        var field = DummyCartCache.class.getDeclaredField("cacheTimestamp");
        field.setAccessible(true);
        field.set(dummyCartCache, System.currentTimeMillis() - 61_000); // expira (mais de 1 min)

        CartListModel result = dummyCartCache.getCarts();

        assertEquals(secondResponse, result);

        verify(dummyJsonClient, times(2)).getCarts();
    }

    @Test
    void shouldClearCache() {

        CartListModel apiResponse = new CartListModel();
        when(dummyJsonClient.getCarts()).thenReturn(apiResponse);

        dummyCartCache.getCarts();
        dummyCartCache.clearCache();

        dummyCartCache.getCarts();

        verify(dummyJsonClient, times(2)).getCarts();
    }
}
