package br.com.ronaldo.market_intelligence.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Objeto user retornado")
public class UserResponseDto {

    @Schema()
    @JsonProperty("id")
    private Long externalId;

    @Schema()
    private String firstName;

    @Schema()
    private String lastName;

    @Schema()
    private String username;

    @Schema()
    private String email;

    @Schema()
    private Integer age;

    @Schema()
    private String gender;
}