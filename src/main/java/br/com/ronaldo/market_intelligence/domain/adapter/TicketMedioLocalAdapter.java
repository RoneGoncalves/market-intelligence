package br.com.ronaldo.market_intelligence.domain.adapter;

import br.com.ronaldo.market_intelligence.domain.entity.UserEntity;
import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.domain.model.CartModel;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioLocalModel;
import br.com.ronaldo.market_intelligence.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TicketMedioLocalAdapter {

    private final UserRepository repository;

    public TicketMedioLocalAdapter(UserRepository repository) {
        this.repository = repository;
    }

    public TicketMedioLocalModel calculaTicketMedioLocal(CartListModel cartListResponseDto) {

        List<UserEntity> usuariosLocais = repository.findAll();

        Map<Long, UserEntity> mapaUsuariosPorExternalId =
                usuariosLocais.stream()
                        .collect(Collectors.toMap(UserEntity::getExternalId, u -> u));

        List<CartModel> cartsLocais = cartListResponseDto.getCarts()
                .stream()
                .filter(cart -> mapaUsuariosPorExternalId.containsKey(cart.getUserId()))
                .toList();

        TicketMedioLocalModel dto = new TicketMedioLocalModel();

        if (cartsLocais.isEmpty()) {
            dto.setTotalCarts(0);
            dto.setTicketMedioLocal(0.0);
            dto.setTicketMedioDescontadoLocal(0.0);
            dto.setMaiorTicketLocal(0.0);
            dto.setMenorTicketLocal(0.0);
            return dto;
        }

        // 3 — Calcular valores
        int totalCarts = cartsLocais.size();

        double somaTotal = cartsLocais.stream().mapToDouble(CartModel::getTotal).sum();
        double somaDescontado = cartsLocais.stream().mapToDouble(CartModel::getDiscountedTotal).sum();

        double ticketMedio = somaTotal / totalCarts;
        double ticketMedioDescontado = somaDescontado / totalCarts;

        double maiorTicket = cartsLocais.stream()
                .mapToDouble(CartModel::getTotal)
                .max()
                .orElse(0.0);

        double menorTicket = cartsLocais.stream()
                .mapToDouble(CartModel::getTotal)
                .min()
                .orElse(0.0);

        // 4 — Preencher DTO
        dto.setTotalCarts(totalCarts);
        dto.setTicketMedioLocal(ticketMedio);
        dto.setTicketMedioDescontadoLocal(ticketMedioDescontado);
        dto.setMaiorTicketLocal(maiorTicket);
        dto.setMenorTicketLocal(menorTicket);

        return dto;
    }
}

