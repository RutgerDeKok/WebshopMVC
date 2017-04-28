package rsvier.address;

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
import rsvier.user.User;
import rsvier.user.UserService;


@Controller
public class AddressController {
    
    @Autowired
    ProductService productService;
    @Autowired
    CartSubOrderService cartSubOrderService;
    @Autowired
    UserService userService;
    @Autowired
    AddressService addressService;

    @RequestMapping("/maakAdres")
    public String maakAdres(Model model, HttpServletRequest request) {
       User user = userService.getUser(1L);
        model.addAttribute("user", user);
        List<Address> addressList =  user.getBillingAddresses();
        model.addAttribute("addressList", addressList);
        return "maakAdres";
    }
    
    @RequestMapping(value="/maakAddress", method = RequestMethod.POST)
    public String voegAdrestoe(HttpServletRequest request, Model model) {
        User user = userService.getUser(1L);
        System.out.println(user.getEmail());
        Address NieuwAddress = new Address();
        NieuwAddress.setFirstName(request.getParameter("firstName"));
        NieuwAddress.setFamilyName(request.getParameter("lastName"));
        NieuwAddress.setInsertion(request.getParameter("addition"));
        NieuwAddress.setCity(request.getParameter("city"));
        NieuwAddress.setNumber(Integer.parseInt(request.getParameter("number")));
        NieuwAddress.setStreet(request.getParameter("street"));
        NieuwAddress.setZipCode(request.getParameter("zipCode"));
        NieuwAddress.setUser(userService.getUser(1L));
        addressService.createAddress(NieuwAddress);
        System.out.println("in maakcontroller, user city = " + NieuwAddress.getCity());
        System.out.println("bij user, user city = " + user.getBillingAddresses().get((user.getBillingAddresses().size())-1).getCity()); 
        
        return "redirect:/wijzigAdres";
    }

    @RequestMapping("/wijzigAdres")
    public String wijzigAdres(Model model, HttpServletRequest request) {
        User user = userService.getUser(1L);
        request.getSession().setAttribute("currentUser", user);
        model.addAttribute("user", user);
        List<Address> addressList =  new ArrayList<>();
        for (Address address : addressService.getAllAddresses()){
            if (address.getUser().getId()==user.getId())
            addressList.add(address);
            }
        model.addAttribute("addressList", addressList);

        return "wijzigAdres";
    }
    @RequestMapping(value = "/wijzigAdres/choose", method = RequestMethod.POST)
    public String kiesAdres(@RequestParam("id") String id, Model model, HttpServletRequest request) {
        User user = userService.getUser(1L);

        model.addAttribute("user", user);
        Address address = user.getBillingAddresses().get(Integer.parseInt("" + id));
        model.addAttribute("address", address);
        return "redirect:/checkout";
    }
   
    @RequestMapping(value = "/wijzigAdres/delete", method = RequestMethod.POST)
    public String deleteAdres(Model model, HttpServletRequest request) {
       Address address = (Address)request.getAttribute("addresId");
 
        addressService.deleteAddress(address);
        return "redirect:/wijzigAdres";
        }
}