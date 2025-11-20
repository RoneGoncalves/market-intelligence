package br.com.ronaldo.market_intelligence.infrastructure.client;

import br.com.ronaldo.market_intelligence.application.dto.DummyUsersResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "usersClient",
        url = "https://dummyjson.com/users")
public interface UserClient {

    @GetMapping("/filter?key=email")
    DummyUsersResponseDto searchUserByEmail(@RequestParam("value") String email);
}
