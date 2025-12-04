package br.com.ronaldo.market_intelligence.domain.service.product;

import br.com.ronaldo.market_intelligence.application.dto.BestSellingProductDto;
import br.com.ronaldo.market_intelligence.domain.adapter.BestSellingProductAdapter;
import br.com.ronaldo.market_intelligence.domain.exception.CartsNotFoundException;
import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.infrastructure.cache.DummyCartCache;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.BestSellingProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BestSellingProductServiceImp {
    private static final Logger log = LoggerFactory.getLogger(BestSellingProductServiceImp.class);

    private final BestSellingProductAdapter bestSellingProductAdapter;
    private final BestSellingProductMapper bestSellingProductMapper;
    private final DummyCartCache dummyCartCache;

    public BestSellingProductServiceImp(BestSellingProductAdapter bestSellingProductAdapter,
                                        BestSellingProductMapper bestSellingProductMapper,
                                        DummyCartCache dummyCartCache) {
        this.bestSellingProductAdapter = bestSellingProductAdapter;
        this.bestSellingProductMapper = bestSellingProductMapper;
        this.dummyCartCache = dummyCartCache;
    }


    public BestSellingProductDto execute() {
       try {
           log.info("[UserClient] - CLIENT REQUEST Buscando Lista de carts: ");
           CartListModel cartListModel = dummyCartCache.getCarts();


           if (cartListModel.getCarts() == null || cartListModel.getCarts().isEmpty()) {

               log.warn("[UserClient] - Nenhum cart retonado pelo DummyJSON ");
               throw new CartsNotFoundException("Nenhum cart retonado pelo DummyJSON ");
           }

           final var bestSellingProductModel = bestSellingProductAdapter.calculaProdutoMaisVendidoDummy(cartListModel);

           return bestSellingProductMapper.toDto(bestSellingProductModel);

       } catch (feign.FeignException error) {
           log.error("Erro ao consultar a API DummyJSON: status={} message={}",error.status(), error.getMessage());

           throw new ExternalApiException(
                   "Erro ao consultar a API DummyJSON: " + error.status(), error
           );
       }
    }
}
