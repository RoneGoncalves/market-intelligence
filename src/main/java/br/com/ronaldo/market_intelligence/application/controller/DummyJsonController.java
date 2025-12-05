package br.com.ronaldo.market_intelligence.application.controller;

import br.com.ronaldo.market_intelligence.application.dto.BestSellingProductDto;
import br.com.ronaldo.market_intelligence.application.dto.TicketMedioResponseDto;
import br.com.ronaldo.market_intelligence.application.dto.CreateUserRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.UserResponseDto;
import br.com.ronaldo.market_intelligence.domain.service.cart.TicketMedioServiceImp;
import br.com.ronaldo.market_intelligence.domain.service.product.BestSellingProductServiceImp;
import br.com.ronaldo.market_intelligence.domain.service.user.CreateUserServiceImp;
import br.com.ronaldo.market_intelligence.domain.service.user.DeleteUserServiceImp;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.BestSellingProductMapper;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.TicketMedioMapper;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.UserMapper;
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

    private final CreateUserServiceImp createUserServiceImp;
    private final TicketMedioServiceImp ticketMedioServiceImp;
    private final DeleteUserServiceImp deleteUserServiceImp;
    private final BestSellingProductServiceImp productService;
    private final TicketMedioMapper ticketMedioMapper;
    private final BestSellingProductMapper bestSellingProductMapper;
    private final UserMapper userMapper;

    public DummyJsonController(CreateUserServiceImp createUserServiceImp, TicketMedioServiceImp ticketMedioServiceImp, DeleteUserServiceImp deleteUserServiceImp, BestSellingProductServiceImp productService, TicketMedioMapper ticketMedioMapper, BestSellingProductMapper bestSellingProductMapper, UserMapper userMapper) {
        this.createUserServiceImp = createUserServiceImp;
        this.ticketMedioServiceImp = ticketMedioServiceImp;
        this.deleteUserServiceImp = deleteUserServiceImp;
        this.productService = productService;
        this.ticketMedioMapper = ticketMedioMapper;
        this.bestSellingProductMapper = bestSellingProductMapper;
        this.userMapper = userMapper;
    }

    @PostMapping("/create_user")
    public ResponseEntity<UserResponseDto> criateUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
        log.info("[UserController] [POST] REQUEST DATA EMAIL: {}", requestDto.getEmail());

        final var request = userMapper.toModel(requestDto);
        final var serviceResponce = createUserServiceImp.execute(request);
        UserResponseDto response = userMapper.toDto(serviceResponce);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Positive(message = "O ID deve ser positivo") Long id) {
        log.info("[DummyJsonController] [DELETE] Solicitada exclusão do usuário ID: {}", id);

        deleteUserServiceImp.excluirUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ticket_medio")
    public ResponseEntity<TicketMedioResponseDto> getTicketMedio() {
        log.info("[DummyJsonController] [GET] Enviando request ticket médio...");

        final var ticketMedioResponse = ticketMedioServiceImp.execute();
        final var response = ticketMedioMapper.toDto(ticketMedioResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/product_selling_analyses")
    public ResponseEntity<BestSellingProductDto> getBestSellingProduct() {
        log.info("[DummyJsonController] [GET] Enviando request best selling product...");

        final var bestSellingProduct = productService.execute();
        BestSellingProductDto response = bestSellingProductMapper.toDto(bestSellingProduct);

        return ResponseEntity.ok(response);
    }
}

