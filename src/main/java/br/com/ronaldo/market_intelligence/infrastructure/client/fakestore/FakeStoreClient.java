package br.com.ronaldo.market_intelligence.infrastructure.client.fakestore;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "fakestore", url = "https://fakestoreapi.com")
public interface FakeStoreClient {

    @GetMapping("/products")
    List<Object> getAllProducts();

    @GetMapping("/products/{id}")
    Object getProductById(@PathVariable("id") Long id);
}
