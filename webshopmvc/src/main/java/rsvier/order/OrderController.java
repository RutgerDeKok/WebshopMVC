package rsvier.order;

import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rsvier.cartsuborder.CartSubOrder;
import rsvier.cartsuborder.CartSubOrderService;
import rsvier.product.Product;
import rsvier.product.ProductCategory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties.Session;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMethod;
import rsvier.address.Address;
import rsvier.product.ProductService;
import rsvier.address.AddressService;
import rsvier.cart.Cart;
import rsvier.cart.CartService;
import rsvier.finalsuborder.FinalSubOrder;
import rsvier.finalsuborder.FinalSubOrderService;
import rsvier.user.User;
import rsvier.user.UserService;


@Controller
public class OrderController {
    
    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;
    @Autowired
    CartSubOrderService cartSubOrderService;
    @Autowired
    UserService userService;
    @Autowired
    AddressService addressService;
    @Autowired
    FinalSubOrderService finalSubOrderService;
    @Autowired
    OrderService orderService;


    @RequestMapping("/confirm")
    public String confirmOrder(Model model, HttpServletRequest request, HttpSession session) {
  
        Cart cart=  (Cart)session.getAttribute("cart");
        User user = (User)session.getAttribute("currentUser");
        Address address;
        
        if (user.getBillingAddress()==null)
            address= new Address();
        else
            address = user.getBillingAddress();
        
        Order order = new Order();
        FinalSubOrder finalSubOrder; 
        List<FinalSubOrder> finalSubOrderList= new ArrayList<>();
        
        
        // CartSubOrder naar FinalSubOrders 
        List<CartSubOrder> subOrderList = cart.getSubOrders();
        for (CartSubOrder subOrder: subOrderList ){
            finalSubOrder = new FinalSubOrder();
            finalSubOrder.setPrd_name(subOrder.getProduct().getName());
            finalSubOrder.setItem_price(subOrder.getProduct().getPrice());
            finalSubOrder.setPrd_brand(subOrder.getProduct().getBrand());
            finalSubOrder.setPrd_category(subOrder.getProduct().getCategory());
            finalSubOrder.setQuantity(subOrder.getQuantity());
            finalSubOrder.setSubTotal(subOrder.getSubTotal());
 
          
            //Stock aanpassen
            Product product = subOrder.getProduct();
            System.out.println(finalSubOrder.getId());
            System.out.println(finalSubOrder.getPrd_name());
            System.out.println(product.getName());
           
            product.setStockCount(product.getStockCount() - finalSubOrder.getQuantity());
            productService.updateProducter(product);
            //FinalSuborder toevoegen in db
            finalSubOrderService.createFinalSubOrder(finalSubOrder);
            //deze toevoegen aan de order
            order.addSubOrder(finalSubOrder);
            //CartSubOrders Verwijderen
            cartSubOrderService.deleteCartSubOrder(subOrder);
            //Cart leegmaken
            //cartService.removeFromCart(cart, subOrder);
          
        }
            
         // Cart naar Order en Cart leegmaken  
         order.setDeliveryAdress(address);
         order.setSaledate(new Date());
         order.setTotalPrice(cart.getTotalPrice());
         order.setUser(user);
         orderService.createOrder(order);
         
         
         // Alleen de totalPrice moet nu nog verwijderd worden, cart met adres blijft
         cart.setTotalPrice(new BigDecimal("0"));
         cartService.updateCart(cart);

        model.addAttribute("address", address);
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        
        return "confirm";
        
         // als een gebruiker is ingelogd, de gebruiker
  // opnieuw opslaan in de dB ,cascade moett er voor zorgen dat wijzigingen 
  // in cart en user (zoals addressen) worden oopgeslagen
 
        
    }
   
}

