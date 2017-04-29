package rsvier.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rsvier.cartsuborder.CartSubOrder;
import rsvier.cartsuborder.CartSubOrderService;
import rsvier.product.Product;
import rsvier.product.ProductCategory;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import rsvier.product.ProductService;
import rsvier.address.Address;
import rsvier.address.AddressService;
import rsvier.user.User;
import rsvier.user.UserService;


@Controller
public class CartController {
    
    @Autowired
    ProductService productService;
    @Autowired
    CartSubOrderService cartSubOrderService;
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    AddressService addressService;

    @RequestMapping("/cart")
    public String shoppingCart(Model model) {
        Cart cart = cartService.getCart(1L);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @RequestMapping(value="/cart/delete", method = RequestMethod.GET)
    public String removeFromCart(@RequestParam long cartId, @RequestParam long subOrderId) {
        Cart cart = cartService.getCart(1L);
        CartSubOrder cso = cartSubOrderService.getCartSubOrder(subOrderId);
        cartService.removeFromCart(cart, cso);
        return "redirect:/cart";
    }

    @RequestMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
       Product product = new Product(1, "kaas", "merk", ProductCategory.GOAT, "info", (new BigDecimal("2.50")), 200);
        CartSubOrder cso = new CartSubOrder(1, product, 5);
        cso.setSubTotal(product.getPrice(), cso.getQuantity());
        CartSubOrder cso2 = new CartSubOrder(2, product, 3);
        cso2.setSubTotal(product.getPrice(), cso2.getQuantity());
        Cart cart = new Cart();
        cart.addSubOrder(cso);
        cart.addSubOrder(cso2);
        cart.calculateTotalPrice();
       
        
        
        User user = userService.getUser(1L);
        Address address;
        
        // als er geen sessieObject voor address aanwezig is, pakt hij het eerste adres
        if (session.getAttribute("addressId")==null)
            address = user.getBillingAddresses().get(0);
        else {
            long addressId = (long)session.getAttribute("addressId");
            address = addressService.getAddress(addressId);
        }
        
        model.addAttribute("address",  address);
        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        return "Checkout";
    }

}