package br.com.ronaldo.market_intelligence.domain.model;

public class ProductModel {
    private Long id;
    private String title;
    private Double price;
    private Integer quantity;
    private Double total;
    private Double discountPercentage;
    private Double discountedTotal;

    public ProductModel() {}

    public ProductModel(Long id, String title, Double price, Integer quantity, Double total, Double discountPercentage, Double discountedTotal) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.discountPercentage = discountPercentage;
        this.discountedTotal = discountedTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Double getDiscountedTotal() {
        return discountedTotal;
    }

    public void setDiscountedTotal(Double discountedTotal) {
        this.discountedTotal = discountedTotal;
    }
}
