package br.com.ronaldo.market_intelligence.domain.service.user;

import br.com.ronaldo.market_intelligence.domain.entity.UserEntity;
import br.com.ronaldo.market_intelligence.domain.exception.UserNotFoundException;
import br.com.ronaldo.market_intelligence.domain.repository.UserRepository;
import br.com.ronaldo.market_intelligence.infrastructure.client.DummyJsonClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeleteUserServiceImpTest {

    @Mock
    private DummyJsonClient dummyJsonClient;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private DeleteUserServiceImp service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void excluirUsuario_shouldThrowException_whenUserNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.excluirUsuario(id));

        verify(repository, times(1)).findById(id);
        verify(repository, never()).deleteById(any());
        verifyNoInteractions(dummyJsonClient);
    }

    @Test
    void excluirUsuario_shouldDeleteUser_whenUserExists() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.of(new UserEntity()));

        service.excluirUsuario(id);

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).deleteById(id);
        verifyNoInteractions(dummyJsonClient);
    }
}
