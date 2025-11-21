package br.com.ronaldo.market_intelligence.domain.adapter;

import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.domain.model.CartModel;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioDummyModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketMedioAdapter {

    public TicketMedioDummyModel calculaTicketMedio(CartListModel cartListModel) {

        TicketMedioDummyModel responseDto = new TicketMedioDummyModel();

        List<CartModel> carts = cartListModel.getCarts();
        if (carts == null || carts.isEmpty()) {
            responseDto.setDummyTotalCarts(0);
            responseDto.setDummyTicketMedio(0.0);
            responseDto.setDummyTicketMedioDescontado(0.0);
            responseDto.setDummyMaiorTicket(0.0);
            responseDto.setDummyMenorTicket(0.0);
            return responseDto;
        }

        int totalCarts = carts.size();

        double somaTotal = carts.stream()
                .mapToDouble(CartModel::getTotal)
                .sum();

        double somaDiscountedTotal = carts.stream()
                .mapToDouble(CartModel::getDiscountedTotal)
                .sum();

        double ticketMedio = somaTotal / totalCarts;
        double ticketMedioDescontado = somaDiscountedTotal / totalCarts;

        double maiorTicket = carts.stream()
                .mapToDouble(CartModel::getTotal)
                .max()
                .orElse(0.0);

        double menorTicket = carts.stream()
                .mapToDouble(CartModel::getTotal)
                .min()
                .orElse(0.0);

        // Preencher DTO
        responseDto.setDummyTotalCarts(totalCarts);
        responseDto.setDummyTicketMedio(ticketMedio);
        responseDto.setDummyTicketMedioDescontado(ticketMedioDescontado);
        responseDto.setDummyMaiorTicket(maiorTicket);
        responseDto.setDummyMenorTicket(menorTicket);

        return responseDto;
    }
}

