package br.com.ronaldo.market_intelligence.domain.adapter;

import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.domain.model.CartModel;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioDummyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TicketMedioAdapterTest {

    private TicketMedioAdapter adapter;

    @BeforeEach
    void setup() {
        adapter = new TicketMedioAdapter();
    }

    @Test
    void shouldReturnZerosWhenCartsIsNull() {
        CartListModel model = new CartListModel();
        model.setCarts(null);

        TicketMedioDummyModel result = adapter.calculaTicketMedio(model);

        assertThat(result.getDummyTotalCarts()).isEqualTo(0);
        assertThat(result.getDummyTicketMedio()).isEqualTo(0.0);
        assertThat(result.getDummyTicketMedioDescontado()).isEqualTo(0.0);
        assertThat(result.getDummyMaiorTicket()).isEqualTo(0.0);
        assertThat(result.getDummyMenorTicket()).isEqualTo(0.0);
    }

    @Test
    void shouldReturnZerosWhenCartsIsEmpty() {
        CartListModel model = new CartListModel();
        model.setCarts(List.of());

        TicketMedioDummyModel result = adapter.calculaTicketMedio(model);

        assertThat(result.getDummyTotalCarts()).isEqualTo(0);
        assertThat(result.getDummyTicketMedio()).isEqualTo(0.0);
        assertThat(result.getDummyTicketMedioDescontado()).isEqualTo(0.0);
        assertThat(result.getDummyMaiorTicket()).isEqualTo(0.0);
        assertThat(result.getDummyMenorTicket()).isEqualTo(0.0);
    }

    @Test
    void shouldCalculateCorrectValuesForSingleCart() {
        CartModel cart = new CartModel();
        cart.setTotal(100.0);
        cart.setDiscountedTotal(80.0);

        CartListModel model = new CartListModel();
        model.setCarts(List.of(cart));

        TicketMedioDummyModel result = adapter.calculaTicketMedio(model);

        assertThat(result.getDummyTotalCarts()).isEqualTo(1);
        assertThat(result.getDummyTicketMedio()).isEqualTo(100.0);
        assertThat(result.getDummyTicketMedioDescontado()).isEqualTo(80.0);
        assertThat(result.getDummyMaiorTicket()).isEqualTo(100.0);
        assertThat(result.getDummyMenorTicket()).isEqualTo(100.0);
    }

    @Test
    void shouldCalculateCorrectValuesForMultipleCarts() {
        CartModel c1 = new CartModel();
        c1.setTotal(100.0);
        c1.setDiscountedTotal(90.0);

        CartModel c2 = new CartModel();
        c2.setTotal(200.0);
        c2.setDiscountedTotal(150.0);

        CartModel c3 = new CartModel();
        c3.setTotal(50.0);
        c3.setDiscountedTotal(40.0);

        CartListModel model = new CartListModel();
        model.setCarts(List.of(c1, c2, c3));

        TicketMedioDummyModel result = adapter.calculaTicketMedio(model);

        assertThat(result.getDummyTotalCarts()).isEqualTo(3);
        assertThat(result.getDummyTicketMedio()).isEqualTo((100 + 200 + 50) / 3.0);
        assertThat(result.getDummyTicketMedioDescontado()).isEqualTo((90 + 150 + 40) / 3.0);
        assertThat(result.getDummyMaiorTicket()).isEqualTo(200.0);
        assertThat(result.getDummyMenorTicket()).isEqualTo(50.0);
    }
}
