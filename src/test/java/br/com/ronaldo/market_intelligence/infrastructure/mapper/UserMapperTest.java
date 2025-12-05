package br.com.ronaldo.market_intelligence.infrastructure.mapper;

import br.com.ronaldo.market_intelligence.domain.entity.UserEntity;
import br.com.ronaldo.market_intelligence.domain.model.CreateUserResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper mapper;

    @BeforeEach
    void setup() {
        mapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void shouldMapDtoToEntityCorrectly() {

        CreateUserResponseModel model = new CreateUserResponseModel(
                10L,
                "John",
                "Doe",
                "johndoe",
                "john@example.com",
                "male",
                28
        );

        UserEntity entity = mapper.toEntity(model);

        assertNotNull(entity);
        assertNull(entity.getId());

        assertEquals(model.getFirstName(), entity.getFirstName());
        assertEquals(model.getLastName(), entity.getLastName());
        assertEquals(model.getUsername(), entity.getUsername());
        assertEquals(model.getEmail(), entity.getEmail());
        assertEquals(model.getGender(), entity.getGender());
        assertEquals(model.getAge(), entity.getAge());

        assertEquals(model.getExternalId(), entity.getExternalId());
    }

    @Test
    void shouldHandleNullInput() {
        UserEntity entity = mapper.toEntity(null);
        assertNull(entity);
    }

    @Test
    void shouldHandleNullFields() {

        CreateUserResponseModel model = new CreateUserResponseModel();
        model.setExternalId(null);
        model.setFirstName(null);
        model.setLastName(null);
        model.setUsername(null);
        model.setEmail(null);
        model.setGender(null);
        model.setAge(null);

        UserEntity entity = mapper.toEntity(model);

        assertNotNull(entity);
        assertNull(entity.getId());

        assertNull(entity.getExternalId());
        assertNull(entity.getFirstName());
        assertNull(entity.getLastName());
        assertNull(entity.getUsername());
        assertNull(entity.getEmail());
        assertNull(entity.getGender());
        assertNull(entity.getAge());
    }
}