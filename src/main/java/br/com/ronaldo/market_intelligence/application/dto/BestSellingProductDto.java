package br.com.ronaldo.market_intelligence.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Busca produto mais vendido nos carts de DummyJSON")
public class BestSellingProductDto {

    @Schema
    private String produtoMaisVendido;

    @Schema
    private Integer qtdProdutoMaisVendido;

    @Schema
    private String produtoMenosVendido;

    @Schema
    private Integer qtdProdutoMenosVendido;

    public BestSellingProductDto() {}

    public BestSellingProductDto(String produtoMaisVendido, Integer qtdProdutoMaisVendido, String produtoMenosVendido,
                                 Integer qtdProdutoMenosVendido) {
        this.produtoMaisVendido = produtoMaisVendido;
        this.qtdProdutoMaisVendido = qtdProdutoMaisVendido;
        this.produtoMenosVendido = produtoMenosVendido;
        this.qtdProdutoMenosVendido = qtdProdutoMenosVendido;
    }

    public String getProdutoMaisVendido() {
        return produtoMaisVendido;
    }

    public void setProdutoMaisVendido(String produtoMaisVendido) {
        this.produtoMaisVendido = produtoMaisVendido;
    }

    public Integer getQtdProdutoMaisVendido() {
        return qtdProdutoMaisVendido;
    }

    public void setQtdProdutoMaisVendido(Integer qtdProdutoMaisVendido) {
        this.qtdProdutoMaisVendido = qtdProdutoMaisVendido;
    }

    public String getProdutoMenosVendido() {
        return produtoMenosVendido;
    }

    public void setProdutoMenosVendido(String produtoMenosVendido) {
        this.produtoMenosVendido = produtoMenosVendido;
    }

    public Integer getQtdProdutoMenosVendido() {
        return qtdProdutoMenosVendido;
    }

    public void setQtdProdutoMenosVendido(Integer qtdProdutoMenosVendido) {
        this.qtdProdutoMenosVendido = qtdProdutoMenosVendido;
    }
}
