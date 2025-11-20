package br.com.ronaldo.market_intelligence.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Objeto retornado nas respostas da API de produtos")
public class DummyUsersResponseDto {
    private List<UserResponseDto> users;
    private int total;
    private int skip;
    private int limit;
}