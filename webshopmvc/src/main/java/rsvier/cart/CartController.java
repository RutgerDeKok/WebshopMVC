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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rsvier.address.Address;
import rsvier.user.User;


@Controller
public class CartController {
    
    @Autowired
    CartService cartService;
    Cart cart = new Cart();
    
    
    @RequestMapping("/cart")
    public String shoppingCart(Model model) {
        Product product = new Product(1, "kaas", "merk", ProductCategory.GOAT, "info", (new BigDecimal("2.50")), 200);
        CartSubOrder cso = new CartSubOrder(1, product, 5);
        cso.setSubTotal(product.getPrice(), cso.getQuantity());
        CartSubOrder cso2 = new CartSubOrder(2, product, 3);
        cso2.setSubTotal(product.getPrice(), cso2.getQuantity());
        cart.addSubOrder(cso);
        cart.addSubOrder(cso2);
        cart.calculateTotalPrice();
        model.addAttribute("cart", cart);
        return "cart";
    }

    @RequestMapping(value="/cart/{id}")
    public String deleteProduct(Model model, @PathVariable String id){
       long idL = Long.parseLong(id);
       
        cart.getSubOrders().remove(id);
        
        model.addAttribute("cart", cart);
        return "cart";
        /* Zo gaat het ongeveer moeten met een DB
        List<CartSubOrder> list = new ArrayList<>();
        list = cartService.getCart(1L).getSubOrders();
        list.remove(id);
        cartService.deleteCart(cart);
        */
    }
     @RequestMapping("/checkout")
    public String checkout(Model model) {
       Product product = new Product(1, "kaas", "merk", ProductCategory.GOAT, "info", (new BigDecimal("2.50")), 200);
        CartSubOrder cso = new CartSubOrder(1, product, 5);
        cso.setSubTotal(product.getPrice(), cso.getQuantity());
        CartSubOrder cso2 = new CartSubOrder(2, product, 3);
        cso2.setSubTotal(product.getPrice(), cso2.getQuantity());
        cart.addSubOrder(cso);
        cart.addSubOrder(cso2);
        cart.calculateTotalPrice();
        model.addAttribute("cart", cart);
        Address address = new Address(2, "Tjeerd", "van", "Santema", "hiero", 22, "b", "9283 AG", "Groningen");
        User user = new User();
        user.setBillingAdress(address);
        model.addAttribute("user", user);
        
        
        return "Checkout";
    }
}