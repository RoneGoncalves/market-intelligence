package br.com.ronaldo.market_intelligence.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Busca por um usuário expecífico no DummyJSON")
public class CreateUserRequestModel {

    private String email;

    public CreateUserRequestModel() {}

    public CreateUserRequestModel(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

