package br.com.ronaldo.market_intelligence.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer age;
    private String email;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "first_name")
    private String firstName;

    private String lastName;
    private String gender;

    @Column(name = "user_name")
    private String username;
}
