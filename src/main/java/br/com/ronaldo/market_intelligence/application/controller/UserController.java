package br.com.ronaldo.market_intelligence.application.controller;

import br.com.ronaldo.market_intelligence.application.dto.UserRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.UserResponseDto;
import br.com.ronaldo.market_intelligence.domain.service.user.CreateUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/produtos")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final CreateUserService createUserService;

    @PostMapping
    public ResponseEntity<UserResponseDto> criar(@Valid @RequestParam String request) {
        log.info("[UserController] Recebida requisição: {}", request);

        var response = createUserService.execute(request);
        return ResponseEntity.ok(response);
    }
}

