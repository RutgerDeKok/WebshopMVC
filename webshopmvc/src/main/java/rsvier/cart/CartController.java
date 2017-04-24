package rsvier.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rsvier.cartsuborder.CartSubOrder;
import rsvier.product.Product;
import rsvier.product.ProductCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class CartController {
    
    @Autowired
    CartService cartService;
    Cart cart = new Cart();
    
    
    @RequestMapping("/cart")
    public Cart shoppingCart(Model model) {
        Product product = new Product(1, "kaas", "merk", ProductCategory.GOAT, "info", (new BigDecimal("2.50")), 200);
        CartSubOrder cso = new CartSubOrder(1, product, 5);
        cso.setSubTotal(product.getPrice(), cso.getQuantity());
        CartSubOrder cso2 = new CartSubOrder(2, product, 3);
        cso2.setSubTotal(product.getPrice(), cso2.getQuantity());
        cart.addSubOrder(cso);
        cart.addSubOrder(cso2);
        model.addAttribute("cart", cart);
        return cart;
    }

    @RequestMapping(method= RequestMethod.DELETE, value="/cart/{id}")
    public void deleteProduct(@PathVariable String id){
       long idL = Long.parseLong(id);
       
        cart.getSubOrders().remove(id);
        
        /* Zo gaat het ongeveer moeten met een DB
        List<CartSubOrder> list = new ArrayList<>();
        list = cartService.getCart(1L).getSubOrders();
        list.remove(id);
        cartService.deleteCart(cart);
        */
    }
     
}