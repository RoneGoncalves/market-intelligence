package br.com.ronaldo.market_intelligence.domain.exception;

public class CartsNotFoundException extends RuntimeException {
    public CartsNotFoundException(String message) {

        super(message);
    }
}
