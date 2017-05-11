/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsvier;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rsvier.address.Address;
import rsvier.cart.Cart;
import rsvier.cart.CartService;
import rsvier.cartsuborder.CartSubOrder;

import rsvier.user.User;
import rsvier.user.UserService;
import rsvier.user.UserType;

/**
 *
 * @author Frank
 */
@Controller
public class RegistreerController {
    
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    // @Autowired
    //         private PassHasher passhasher;
    //opent de pagina registreren van de html.
    @RequestMapping(value = {"/registreren"})
    public String inlog(HttpSession iets) {
//        Eerste keer een leeg adres
        if (iets.getAttribute("tempAdres") == null) {

            Address nieuweAdres = new Address();
            iets.setAttribute("tempAdres", nieuweAdres);
        }
        return "registreren";
    }

    //Dit moet een account aanmaken.
    @RequestMapping(value = "/MaakAccount/ok", method = RequestMethod.POST)
    public String voegAccountToe(HttpServletRequest request,HttpSession session) {

        
        session.removeAttribute("message");
        session.removeAttribute("nieuweAdress");
        System.out.println("Account gegevens worden verwerkt");
        // Waarom print hij hier Klant en niet CUSTOMER?
        String userType = request.getParameter("userType");
        if (userType == null) {
            userType = "CUSTOMER";
        }
        UserType accountType = UserType.valueOf(userType);
        System.out.println(accountType);
        User newUser = userService.registerUser(request, accountType);

            
        
        
        //CONTROLE EMAIL AL BESTAAT!
        
        boolean emailvalid = true;
        
        try{
            
        User dommeuser = userService.findUserByEmail(request.getParameter("email"));
        dommeuser.getEmail();
        System.out.println("Email bestaat al");
        
        emailvalid = false;
        
        }
        catch(Exception e ){e.getMessage(); System.out.println("Deze email is nog niet in de DB- dus validemail = true"); }
        
        
        if(!emailvalid){
            System.out.println(" email is niet valid");
            
            
            session.setAttribute("tempAdres", newUser.getBillingAddress());
          
        int message = 13;
        session.setAttribute("message",message);
            return "redirect:/registreren";
        }
        
        
        
        
        
        
        
        //Deel 2 DB.
        // dan wijzigingen opslaan in DB (cart of user)
        //deze if is niet nodig toch, want er is een nieuwe en niks anders nodig?
        //if(request.getSession().getAttribute("currentUser")!=null){
        System.out.println("Gegevens worden opgeslagen in DB");
        //maakt de user!
        userService.addUser(newUser);
        //Maakt een lege suborder array.
        List<CartSubOrder> subs = new ArrayList<>();
        //Maakt een cart
        Cart newCart = new Cart();
        //linkt subs met cart
        newCart.setSubOrders(subs);
        //linkt cart met de user.
        newCart.setUser(newUser);
        //Geeft cart zelfde id als de nieuwe user (om parrallel te lopen gemak)
        newCart.setId(newUser.getId());
        //sla de cart op
        cartService.createCart(newCart);
        //check
        System.out.println("tot hier gaat het nog goed-Einde DB opslaan.");
        
                
        return "redirect:/login";
    }
    
    
}
