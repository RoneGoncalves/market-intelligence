package br.com.ronaldo.market_intelligence.application.controller;

import br.com.ronaldo.market_intelligence.application.dto.ProductRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.ProductResponseDto;
import br.com.ronaldo.market_intelligence.domain.service.ProductDomainService;
import br.com.ronaldo.market_intelligence.domain.service.product.CreateProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/produtos")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final CreateProductService createProductService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> criar(@Valid @RequestBody ProductRequestDto request) {
        log.info("[ProductController] Recebida requisição: {}", request);

        var response = createProductService.execute(request);
        return ResponseEntity.ok(response);
    }
}

