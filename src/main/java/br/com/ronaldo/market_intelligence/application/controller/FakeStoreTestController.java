package br.com.ronaldo.market_intelligence.application.controller;

import br.com.ronaldo.market_intelligence.infrastructure.client.fakestore.FakeStoreClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeStoreTestController {

    private final FakeStoreClient client;

    public FakeStoreTestController(FakeStoreClient client) {
        this.client = client;
    }

    @GetMapping("/test/fakestore")
    public Object testFakeStore() {
        System.out.println("🔍 Chamando FakeStore API...");
        Object result = client.getAllProducts();
        System.out.println("📦 Resposta recebida!");
        return result;
    }
}

