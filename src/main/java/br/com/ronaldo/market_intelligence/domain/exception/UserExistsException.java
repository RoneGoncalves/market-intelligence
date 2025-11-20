package br.com.ronaldo.market_intelligence.domain.exception;

public class UserExistsException extends RuntimeException{
    public UserExistsException(String message) {
        super(message);
    }
}