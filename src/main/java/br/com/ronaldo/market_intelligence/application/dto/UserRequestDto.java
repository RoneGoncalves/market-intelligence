package br.com.ronaldo.market_intelligence.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Busca por um usuário expecífico no DummyJSON")
public class UserRequestDto {

    @Schema(description = "email do usuário buscado")
    @NotBlank(message = "email é obrigatório")
    private String email;

    public UserRequestDto() {}

    public UserRequestDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

