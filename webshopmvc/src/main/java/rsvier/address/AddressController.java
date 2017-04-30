package rsvier.address;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rsvier.cartsuborder.CartSubOrderService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import rsvier.address.Address;
import rsvier.product.ProductService;
import rsvier.address.AddressService;
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

    @RequestMapping(value="/maakAdres", method = RequestMethod.GET)
    public String maakAdres() {
   
        return "maakAdres";
    }
    
    @RequestMapping(value="/maakAddress", method = RequestMethod.POST)
    public String voegAdrestoe(HttpServletRequest request, Model model) {
    	
        Address NieuwAddress = new Address();
        NieuwAddress.setFirstName(request.getParameter("firstName"));
        NieuwAddress.setFamilyName(request.getParameter("lastName"));
        NieuwAddress.setInsertion(request.getParameter("addition"));
        NieuwAddress.setCity(request.getParameter("city"));
        NieuwAddress.setNumber(Integer.parseInt(request.getParameter("number")));
        NieuwAddress.setStreet(request.getParameter("street"));
        NieuwAddress.setZipCode(request.getParameter("zipCode"));
        
        // nu is niet het moment om op te slaan in DB
//        addressService.createAddress(NieuwAddress);
        
        // nieuw address opslaan in sessie
        request.getSession().setAttribute("newAddress", NieuwAddress);
        
        
        return "redirect:/wijzigAdres";
    }
    
    
    
    @RequestMapping(value="/wijzigAdres", method = RequestMethod.GET)
    public String wijzigAdres() {

        return "wijzigAdres";
    }
    
    
    @RequestMapping(value = "/wijzigAdres/choose")
    public String kiesAdres(@RequestParam("addressId") String addressId, Model model, HttpSession session) {
        long realAddressId = Long.parseLong(addressId);
        session.setAttribute("addressId", realAddressId);
        return "redirect:/checkout";
    }
   
    @RequestMapping(value = "/wijzigAdres/delete")
    public String deleteAdres(@RequestParam("addressId") String addressId, Model model, HttpServletRequest request) {
        Address address = addressService.getAddress(Long.parseLong(addressId));
 
        addressService.deleteAddress(address);
        return "redirect:/wijzigAdres";
        }
}