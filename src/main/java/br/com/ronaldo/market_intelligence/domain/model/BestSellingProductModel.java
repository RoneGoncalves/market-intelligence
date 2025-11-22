package br.com.ronaldo.market_intelligence.domain.model;

public class BestSellingProductModel {

    private String produtoMaisVendido;
    private Integer qtdProdutoMaisVendido;
    private String produtoMenosVendido;
    private Integer qtdProdutoMenosVendido;

    public BestSellingProductModel() {}

    public BestSellingProductModel(String produtoMaisVendido, Integer qtdProdutoMaisVendido, String produtoMenosVendido, Integer qtdProdutoMenosVendido) {
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
