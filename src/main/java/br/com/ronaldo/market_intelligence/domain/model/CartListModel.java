package br.com.ronaldo.market_intelligence.domain.model;

import java.util.List;

public class CartListModel {
    private List<CartModel> carts;
    private Integer total;
    private Integer skip;
    private Integer limit;

    public CartListModel() {}

    public CartListModel(List<CartModel> carts, Integer total, Integer skip, Integer limit) {
        this.carts = carts;
        this.total = total;
        this.skip = skip;
        this.limit = limit;
    }

    public List<CartModel> getCarts() {
        return carts;
    }

    public void setCarts(List<CartModel> carts) {
        this.carts = carts;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
