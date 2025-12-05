package br.com.ronaldo.market_intelligence.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Busca por um usuário expecífico no DummyJSON")
public class CreateUserRequestDto {

    @Email(message = "Email inválido")
    @NotBlank(message = "email é obrigatório")
    @Schema(description = "email do usuário buscado")
    private String email;

    public CreateUserRequestDto() {}

    public CreateUserRequestDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

