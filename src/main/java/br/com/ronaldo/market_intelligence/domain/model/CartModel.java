package br.com.ronaldo.market_intelligence.domain.model;

import java.util.List;

public class CartModel {
    private Long id;
    private Long userId;
    private List<ProductModel> products;
    private Integer totalProducts;
    private Integer totalQuantity;
    private Double total;
    private Double discountedTotal;

    public CartModel() {}

    public CartModel(Long id, Long userId, List<ProductModel> products, Integer totalProducts, Integer totalQuantity, Double total, Double discountedTotal) {
        this.id = id;
        this.userId = userId;
        this.products = products;
        this.totalProducts = totalProducts;
        this.totalQuantity = totalQuantity;
        this.total = total;
        this.discountedTotal = discountedTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public Integer getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDiscountedTotal() {
        return discountedTotal;
    }

    public void setDiscountedTotal(Double discountedTotal) {
        this.discountedTotal = discountedTotal;
    }
}
