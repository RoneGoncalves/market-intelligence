package br.com.ronaldo.market_intelligence.application.controller;

import br.com.ronaldo.market_intelligence.application.dto.BestSellingProductDto;
import br.com.ronaldo.market_intelligence.application.dto.TicketMedioResponseDto;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioLocalModel;
import br.com.ronaldo.market_intelligence.application.dto.UserRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.UserResponseDto;
import br.com.ronaldo.market_intelligence.domain.service.cart.TicketMedioService;
import br.com.ronaldo.market_intelligence.domain.service.product.BestSellingProductService;
import br.com.ronaldo.market_intelligence.domain.service.user.CreateUserService;
import br.com.ronaldo.market_intelligence.domain.service.user.DeleteUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final DeleteUserService deleteUserService;
    private final BestSellingProductService productService;

    public DummyJsonController(CreateUserService createUserService, TicketMedioService ticketMedioService, DeleteUserService deleteUserService, BestSellingProductService productService) {
        this.createUserService = createUserService;
        this.ticketMedioService = ticketMedioService;
        this.deleteUserService = deleteUserService;
        this.productService = productService;
    }

    @PostMapping("/create_user")
    public ResponseEntity<UserResponseDto> criateUser(@Valid @RequestBody UserRequestDto request) {
        log.info("[UserController] [POST] REQUEST DATA EMAIL: {}", request.getEmail());

        UserResponseDto response = createUserService.execute(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Positive(message = "O ID deve ser positivo") Long id) {
        log.info("[DummyJsonController] [DELETE] Solicitada exclusão do usuário ID: {}", id);

        deleteUserService.excluirUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ticket_medio")
    public ResponseEntity<TicketMedioResponseDto> getTicketMedio() {
        log.info("[DummyJsonController] [GET] Enviando request ticket médio...");

        TicketMedioResponseDto response = ticketMedioService.execute();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product_selling_analyses")
    public ResponseEntity<BestSellingProductDto> getBestSellingProduct() {
        log.info("[DummyJsonController] [GET] Enviando request best selling product...");

        BestSellingProductDto response = productService.execute();
        return ResponseEntity.ok(response);
    }
}

