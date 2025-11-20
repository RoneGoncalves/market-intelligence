package br.com.ronaldo.market_intelligence.infrastructure.mapper;

import br.com.ronaldo.market_intelligence.application.dto.UserResponseDto;
import br.com.ronaldo.market_intelligence.domain.model.UserEntity;
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

        UserResponseDto dto = new UserResponseDto(
                10L,
                "John",
                "Doe",
                "johndoe",
                "john@example.com",
                "male",
                28
        );

        UserEntity entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertNull(entity.getId());

        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEquals(dto.getUsername(), entity.getUsername());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getGender(), entity.getGender());
        assertEquals(dto.getAge(), entity.getAge());

        assertEquals(dto.getExternalId().toString(), entity.getExternalId());
    }

    @Test
    void shouldHandleNullInput() {
        UserEntity entity = mapper.toEntity(null);
        assertNull(entity);
    }

    @Test
    void shouldHandleNullFields() {

        UserResponseDto dto = new UserResponseDto();
        dto.setExternalId(null);
        dto.setFirstName(null);
        dto.setLastName(null);
        dto.setUsername(null);
        dto.setEmail(null);
        dto.setGender(null);
        dto.setAge(null);

        UserEntity entity = mapper.toEntity(dto);

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