package rsvier.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rsvier.cartsuborder.CartSubOrder;
import rsvier.product.Product;
import rsvier.product.ProductCategory;

import java.math.BigDecimal;

/**
 * Created by J on 4/21/2017.
 */
@Controller
public class CartController {

    @RequestMapping("/cart")
    public Cart shoppingCart(Model model) {
        Product product = new Product(1, "kaas", "merk", ProductCategory.GOAT, "info", (new BigDecimal("2.50")), 200);
        CartSubOrder cso = new CartSubOrder(1, product, 5);
        cso.setSubTotal(product.getPrice(), cso.getQuantity());
        CartSubOrder cso2 = new CartSubOrder(2, product, 3);
        cso2.setSubTotal(product.getPrice(), cso2.getQuantity());
        Cart cart = new Cart();
        cart.addSubOrder(cso);
        cart.addSubOrder(cso2);
        model.addAttribute("cart", cart);
        return cart;
    }

}