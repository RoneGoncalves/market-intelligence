package br.com.ronaldo.market_intelligence.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto user retornado")
public class CreateUserResponseDto {

    @Schema()
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

    public CreateUserResponseDto() {}

    public CreateUserResponseDto(Long externalId, String firstName, String lastName, String username, String email, String gender, Integer age) {
        this.externalId = externalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.age = age;
    }

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}