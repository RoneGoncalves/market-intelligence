package br.com.ronaldo.market_intelligence.infrastructure.client;

import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.application.dto.DummyUsersResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "dummyJsonClient",
        url = "https://dummyjson.com")
public interface DummyJsonClient {

    @GetMapping("/users/filter?key=email")
    DummyUsersResponseDto searchUserByEmail(@RequestParam("value") String email);

    @GetMapping("/carts?limit=0")
    CartListModel getCarts();
}
