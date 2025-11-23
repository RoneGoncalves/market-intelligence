package br.com.ronaldo.market_intelligence.domain.adapter;

import br.com.ronaldo.market_intelligence.domain.model.BestSellingProductModel;
import br.com.ronaldo.market_intelligence.domain.model.CartListModel;
import br.com.ronaldo.market_intelligence.domain.model.CartModel;
import br.com.ronaldo.market_intelligence.domain.model.ProductModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BestSellingProductAdapterTest {

    private BestSellingProductAdapter adapter;

    @BeforeEach
    void setup() {
        adapter = new BestSellingProductAdapter();
    }

    private CartListModel buildCartList(ProductModel... products) {
        CartModel cart = new CartModel();
        cart.setProducts(List.of(products));

        CartListModel listModel = new CartListModel();
        listModel.setCarts(List.of(cart));

        return listModel;
    }

    private ProductModel buildProduct(String title, int quantity) {
        ProductModel p = new ProductModel();
        p.setTitle(title);
        p.setQuantity(quantity);
        return p;
    }

    @Test
    void shouldCalculateMostAndLeastSoldProduct() {
        CartListModel carts = buildCartList(
                buildProduct("Macbook", 5),
                buildProduct("Iphone", 2),
                buildProduct("Mouse", 1)
        );

        BestSellingProductModel result = adapter.calculaProdutoMaisVendidoDummy(carts);

        assertEquals("Macbook", result.getProdutoMaisVendido());
        assertEquals(5, result.getQtdProdutoMaisVendido());

        assertEquals("Mouse", result.getProdutoMenosVendido());
        assertEquals(1, result.getQtdProdutoMenosVendido());
    }

    @Test
    void shouldSumQuantitiesOfSameProductAcrossCarts() {
        CartModel cart1 = new CartModel();
        cart1.setProducts(List.of(
                buildProduct("Macbook", 3),
                buildProduct("Iphone", 1)
        ));

        CartModel cart2 = new CartModel();
        cart2.setProducts(List.of(
                buildProduct("Macbook", 4), // total = 7
                buildProduct("Iphone", 2)   // total = 3
        ));

        CartListModel carts = new CartListModel();
        carts.setCarts(List.of(cart1, cart2));

        BestSellingProductModel result = adapter.calculaProdutoMaisVendidoDummy(carts);

        assertEquals("Macbook", result.getProdutoMaisVendido());
        assertEquals(7, result.getQtdProdutoMaisVendido());

        assertEquals("Iphone", result.getProdutoMenosVendido());
        assertEquals(3, result.getQtdProdutoMenosVendido());
    }

    @Test
    void shouldHandleTieSelectingAnyMaxOrMinCorrectly() {
        CartListModel carts = buildCartList(
                buildProduct("Macbook", 5),
                buildProduct("Iphone", 5),
                buildProduct("Mouse", 5)
        );

        BestSellingProductModel result = adapter.calculaProdutoMaisVendidoDummy(carts);

        assertNotNull(result.getProdutoMaisVendido());
        assertEquals(5, result.getQtdProdutoMaisVendido());

        // Como todos têm 5, tanto o mais vendido quanto o menos vendido também serão 5
        assertNotNull(result.getProdutoMenosVendido());
        assertEquals(5, result.getQtdProdutoMenosVendido());
    }

    @Test
    void shouldFailWhenNoProductsExist() {
        CartListModel carts = new CartListModel();
        carts.setCarts(List.of());

        Exception ex = assertThrows(Exception.class, () ->
                adapter.calculaProdutoMaisVendidoDummy(carts)
        );

        assertInstanceOf(NullPointerException.class, ex);
    }
}
