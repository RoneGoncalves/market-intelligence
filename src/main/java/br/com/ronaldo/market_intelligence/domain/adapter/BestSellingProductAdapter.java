package br.com.ronaldo.market_intelligence.domain.adapter;

import br.com.ronaldo.market_intelligence.domain.model.BestSellingProductModel;
import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.domain.model.ProductModel;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BestSellingProductAdapter {

    public BestSellingProductModel calculaProdutoMaisVendidoDummy(CartListModel cartListModel) {

        BestSellingProductModel responseModel = new BestSellingProductModel();

        Map<String, Integer> productQuantities = cartListModel.getCarts().stream()
                .flatMap(cart -> cart.getProducts().stream())
                .collect(Collectors.toMap(
                        ProductModel::getTitle,
                        ProductModel::getQuantity,
                        Integer::sum
                ));

        Map.Entry<String, Integer> maisVendido = productQuantities.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        Map.Entry<String, Integer> menosVendido = productQuantities.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .orElse(null);

        responseModel.setProdutoMaisVendido(maisVendido.getKey());
        responseModel.setQtdProdutoMaisVendido(maisVendido.getValue());
        responseModel.setProdutoMenosVendido(menosVendido.getKey());
        responseModel.setQtdProdutoMenosVendido(menosVendido.getValue());

        return responseModel;
    }
}


