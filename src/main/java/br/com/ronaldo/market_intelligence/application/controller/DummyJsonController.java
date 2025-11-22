package br.com.ronaldo.market_intelligence.application.controller;

import br.com.ronaldo.market_intelligence.application.dto.TicketMedioResponseDto;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioLocalModel;
import br.com.ronaldo.market_intelligence.application.dto.UserRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.UserResponseDto;
import br.com.ronaldo.market_intelligence.domain.service.cart.TicketMedioService;
import br.com.ronaldo.market_intelligence.domain.service.user.CreateUserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DummyJsonController {
    private static final Logger log = LoggerFactory.getLogger(DummyJsonController.class);

    private final CreateUserService createUserService;
    private final TicketMedioService ticketMedioService;

    public DummyJsonController(CreateUserService createUserService, TicketMedioService ticketMedioService) {
        this.createUserService = createUserService;
        this.ticketMedioService = ticketMedioService;
    }

    @PostMapping("/create_user")
    public ResponseEntity<UserResponseDto> criateUser(@Valid @RequestBody UserRequestDto request) {
        log.info("[UserController] REQUEST DATA EMAIL: {}", request.getEmail());

        UserResponseDto response = createUserService.execute(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ticket_medio")
    public ResponseEntity<TicketMedioResponseDto> getTicketMedio() {
        log.info("[DummyJsonController] Enviando request ticket médio...");

        TicketMedioResponseDto response = ticketMedioService.execute();
        return ResponseEntity.ok(response);
    }
}

