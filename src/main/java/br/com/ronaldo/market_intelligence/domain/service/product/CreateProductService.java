package br.com.ronaldo.market_intelligence.domain.service.product;

import br.com.ronaldo.market_intelligence.application.dto.ProductRequestDto;
import br.com.ronaldo.market_intelligence.application.dto.ProductResponseDto;
import br.com.ronaldo.market_intelligence.domain.service.ProductDomainService;
import br.com.ronaldo.market_intelligence.infrastructure.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateProductService {

    private final ProductDomainService domainService;
    private final ProductMapper mapper;

    public ProductResponseDto execute(ProductRequestDto request) {
        log.info("[CreateProductService] Iniciando criação de produto. Request={}", request);

        var entity = mapper.toEntity(request);
        var saved = domainService.salvar(entity);

        log.info("[CreateProductService] Produto salvo com sucesso. id={}, nome={}",
                saved.getId(),
                saved.getNome());

        return mapper.toResponse(saved);
    }
}

