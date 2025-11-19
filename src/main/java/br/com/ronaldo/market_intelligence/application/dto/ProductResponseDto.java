package br.com.ronaldo.market_intelligence.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "Objeto retornado nas respostas da API de produtos")
public class ProductResponseDto {

    @Schema()
    private Long id;

    @Schema()
    private String nome;

    @Schema()
    private String descricao;

    @Schema()
    private Double preco;

    @Schema()
    private String categoria;

    @Schema()
    private String origem;
}

