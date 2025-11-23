package br.com.ronaldo.market_intelligence.infrastructure.cache;

import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.infrastructure.client.DummyJsonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DummyCartCache {

    private static final Logger log = LoggerFactory.getLogger(DummyCartCache.class);

    private final DummyJsonClient dummyJsonClient;

    private CartListModel cachedCarts;

    private long cacheTimestamp = 0;

    private static final long CACHE_DURATION = 30_000;

    public DummyCartCache(DummyJsonClient dummyJsonClient) {
        this.dummyJsonClient = dummyJsonClient;
    }

    public synchronized CartListModel getCarts() {

        long now = System.currentTimeMillis();

        if (cachedCarts != null && (now - cacheTimestamp) < CACHE_DURATION) {
            log.info("[DummyCartCache] Retornando carts do CACHE");
            return cachedCarts;
        }

        log.info("[DummyCartCache] Cache expirado ou vazio. Consultando DummyJSON...");

        CartListModel freshCarts = dummyJsonClient.getCarts();

        cachedCarts = freshCarts;
        cacheTimestamp = now;

        log.info("[DummyCartCache] Cache atualizado com nova lista de carts");

        return freshCarts;
    }

    public synchronized void clearCache() {
        cachedCarts = null;
        cacheTimestamp = 0;
        log.info("[DummyCartCache] Cache limpo manualmente");
    }
}
