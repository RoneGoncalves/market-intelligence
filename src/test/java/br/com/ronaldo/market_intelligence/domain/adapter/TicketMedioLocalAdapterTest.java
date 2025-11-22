package br.com.ronaldo.market_intelligence.domain.adapter;

import br.com.ronaldo.market_intelligence.domain.entity.UserEntity;
import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.domain.model.CartModel;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioLocalModel;
import br.com.ronaldo.market_intelligence.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class TicketMedioLocalAdapterTest {

    private UserRepository repository;
    private TicketMedioLocalAdapter adapter;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(UserRepository.class);
        adapter = new TicketMedioLocalAdapter(repository);
    }

    @Test
    void shouldReturnZeroWhenNoLocalUsersExist() {
        when(repository.findAll()).thenReturn(List.of());

        CartModel cart = new CartModel();
        cart.setUserId(10L);
        cart.setTotal(100.0);
        cart.setDiscountedTotal(80.0);

        CartListModel list = new CartListModel();
        list.setCarts(List.of(cart));

        TicketMedioLocalModel result = adapter.calculaTicketMedioLocal(list);

        assertThat(result.getTotalCarts()).isEqualTo(0);
        assertThat(result.getTicketMedioLocal()).isEqualTo(0.0);
    }

    @Test
    void shouldReturnZeroWhenCartsDoNotBelongToLocalUsers() {
        UserEntity user = new UserEntity();
        user.setExternalId(1L);

        when(repository.findAll()).thenReturn(List.of(user));

        CartModel cart = new CartModel();
        cart.setUserId(99L);
        cart.setTotal(100.0);
        cart.setDiscountedTotal(80.0);

        CartListModel list = new CartListModel();
        list.setCarts(List.of(cart));

        TicketMedioLocalModel result = adapter.calculaTicketMedioLocal(list);

        assertThat(result.getTotalCarts()).isEqualTo(0);
        assertThat(result.getTicketMedioLocal()).isEqualTo(0.0);
    }

    @Test
    void shouldCalculateCorrectValuesForOneLocalCart() {
        UserEntity user = new UserEntity();
        user.setExternalId(10L);

        when(repository.findAll()).thenReturn(List.of(user));

        CartModel cart = new CartModel();
        cart.setUserId(10L);
        cart.setTotal(120.0);
        cart.setDiscountedTotal(100.0);

        CartListModel list = new CartListModel();
        list.setCarts(List.of(cart));

        TicketMedioLocalModel result = adapter.calculaTicketMedioLocal(list);

        assertThat(result.getTotalCarts()).isEqualTo(1);
        assertThat(result.getTicketMedioLocal()).isEqualTo(120.0);
        assertThat(result.getTicketMedioDescontadoLocal()).isEqualTo(100.0);
        assertThat(result.getMaiorTicketLocal()).isEqualTo(120.0);
        assertThat(result.getMenorTicketLocal()).isEqualTo(120.0);
    }

    @Test
    void shouldCalculateCorrectValuesForMultipleLocalCarts() {
        UserEntity user = new UserEntity();
        user.setExternalId(5L);

        when(repository.findAll()).thenReturn(List.of(user));

        CartModel c1 = new CartModel();
        c1.setUserId(5L);
        c1.setTotal(100.0);
        c1.setDiscountedTotal(80.0);

        CartModel c2 = new CartModel();
        c2.setUserId(5L);
        c2.setTotal(300.0);
        c2.setDiscountedTotal(250.0);

        CartListModel list = new CartListModel();
        list.setCarts(List.of(c1, c2));

        TicketMedioLocalModel result = adapter.calculaTicketMedioLocal(list);

        assertThat(result.getTotalCarts()).isEqualTo(2);
        assertThat(result.getTicketMedioLocal()).isEqualTo((100 + 300) / 2.0);
        assertThat(result.getTicketMedioDescontadoLocal()).isEqualTo((80 + 250) / 2.0);
        assertThat(result.getMaiorTicketLocal()).isEqualTo(300.0);
        assertThat(result.getMenorTicketLocal()).isEqualTo(100.0);
    }

    @Test
    void shouldFilterOnlyLocalUserCarts() {
        UserEntity user = new UserEntity();
        user.setExternalId(3L);

        when(repository.findAll()).thenReturn(List.of(user));

        CartModel c1 = new CartModel();
        c1.setUserId(3L); // local
        c1.setTotal(50.0);
        c1.setDiscountedTotal(40.0);

        CartModel c2 = new CartModel();
        c2.setUserId(99L); // NÃO local
        c2.setTotal(999.0);
        c2.setDiscountedTotal(500.0);

        CartListModel list = new CartListModel();
        list.setCarts(List.of(c1, c2));

        TicketMedioLocalModel result = adapter.calculaTicketMedioLocal(list);

        assertThat(result.getTotalCarts()).isEqualTo(1);
        assertThat(result.getTicketMedioLocal()).isEqualTo(50.0);
        assertThat(result.getMaiorTicketLocal()).isEqualTo(50.0);
        assertThat(result.getMenorTicketLocal()).isEqualTo(50.0);
    }
}
