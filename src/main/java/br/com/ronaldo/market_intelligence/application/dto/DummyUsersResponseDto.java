package br.com.ronaldo.market_intelligence.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Objeto retornado nas respostas da API de produtos")
public class DummyUsersResponseDto {
    private List<UserResponseDto> users;
    private int total;
    private int skip;
    private int limit;

    public DummyUsersResponseDto() {}

    public DummyUsersResponseDto(List<UserResponseDto> users, int total, int skip, int limit) {
        this.users = users;
        this.total = total;
        this.skip = skip;
        this.limit = limit;
    }

    public List<UserResponseDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponseDto> users) {
        this.users = users;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}