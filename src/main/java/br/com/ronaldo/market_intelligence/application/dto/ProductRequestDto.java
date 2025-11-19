package br.com.ronaldo.market_intelligence.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Objeto usado para criar ou atualizar um produto")
public class ProductRequestDto {

    @Schema(description = "Nome do produto", example = "Mouse Gamer RGB")
    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Mouse com iluminação RGB, 7200 DPI")
    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @Schema(description = "Preço do produto", example = "199.90")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;

    @Schema(description = "Cateroria do produto", example = "celular")
    @NotBlank(message = "A categoria é obrigatória")
    private String categoria;

    @Schema(description = "Origem do preço do produto", example = "LOCAL")
    @NotBlank(message = "A origem é obrigatória")
    private String origem;
}

