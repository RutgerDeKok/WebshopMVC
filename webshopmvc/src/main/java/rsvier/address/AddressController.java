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
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import rsvier.address.Address;
import rsvier.product.ProductService;
import rsvier.address.AddressService;
import rsvier.user.User;
import rsvier.user.UserService;


@Controller
public class AddressController {
    
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

    @RequestMapping("/wijzigAdres")
    public String wijzigAdres(Model model) {
        User user = userService.getUser(1L);
        model.addAttribute("user", user);
        List<Address> addressList =  user.getBillingAddresses();
      
        
        model.addAttribute("addressList", addressList);
        return "wijzigAdres";
    }

    @RequestMapping("/maakAdres")
    public String maakAdres(Model model) {
        User user = userService.getUser(1L);
        model.addAttribute("user", user);

        return "MaakAdres";
    }
   

}