package rsvier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rsvier.address.Address;
import rsvier.cart.Cart;
import rsvier.cart.CartService;
import rsvier.cartsuborder.CartSubOrder;
import rsvier.infrastructure.PassHasher;
import rsvier.user.User;
import rsvier.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static rsvier.user.UserType.CUSTOMER;

/**
 *
 * @author Frank
 */
@Controller
public class RegisterController {
    
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

   
    @RequestMapping(value = {"/register"})
    public String inlog(HttpSession iets) {
//        Eerste keer een leeg adres
        if (iets.getAttribute("tempAdres") == null) {

            Address nieuweAdres = new Address();
            iets.setAttribute("tempAdres", nieuweAdres);
        }
        return "register";
    }

    
    @RequestMapping(value = "/MaakAccount/ok", method = RequestMethod.POST)
    public String voegAccountToe(HttpServletRequest request, HttpSession session) {

        session.removeAttribute("message");
        session.removeAttribute("nieuweAdress");
        System.out.println("Account gegevens worden verwerkt");

        User nieuweUser = new User();
        Address nieuweAdres = new Address();

        //Linkt user met adres!
        nieuweUser.setBillingAdress(nieuweAdres);

        String input1 = request.getParameter("email");
        System.out.println(input1);
        String input2 = request.getParameter("W8");
        System.out.println(input2);
        try {
            nieuweUser.setEmail(input1);
            //Het is altijd een klant op deze manier.
            nieuweUser.setUserType(CUSTOMER);
        } catch (Exception een) {
            System.out.println(een.getMessage());
        }

        //wachtwoord is nog een tekst, verander naar chars.
        char[] wachtwoordChars = input2.toCharArray();
        //Laat wachtwoord zien (check) in system out.

        System.out.println("tot hier gaat het nog goed.");

        try {

            String GehashdeWachtwoord = PassHasher.getSaltedHash(wachtwoordChars);

            nieuweUser.setPassHash(GehashdeWachtwoord);
        } catch (Exception twee) {
            System.out.println(twee.getMessage());
            System.out.println("Het wachtwoord opslaan gaat fout");
        }

        System.out.println("tot hier gaat het nog goed-2.");

//                adres gedeelte invullen.
        System.out.println("Adres gegevens worden verwerkt");

        nieuweAdres.setFirstName(request.getParameter("firstName"));
        nieuweAdres.setFamilyName(request.getParameter("lastName"));
        nieuweAdres.setInsertion(request.getParameter("insertion"));
        nieuweAdres.setCity(request.getParameter("city"));
        nieuweAdres.setNumber(Integer.parseInt(request.getParameter("number")));
        nieuweAdres.setNumAddition(request.getParameter("addition"));
        nieuweAdres.setStreet(request.getParameter("street"));
        nieuweAdres.setZipCode(request.getParameter("zipCode"));

        boolean emailvalid = true;

        try {

            User dommeuser = userService.findUserByEmail(input1);
            System.out.println(dommeuser.getEmail());
            System.out.println("Email bestaat al");

            emailvalid = false;

        } catch (Exception e) {
            e.getMessage();
            System.out.println("Deze email is nog niet in de DB- dus validemail = true");
        }
        
        }
        catch(Exception e ){e.getMessage(); System.out.println("Deze email is nog niet in de DB- dus validemail = true"); }
        
        
        
        if(!emailvalid){
            System.out.println(" email is niet valid");
                     
            session.setAttribute("tempAdres", nieuweAdres);          
          
        int message = 13;
        session.setAttribute("message",message);
            return "redirect:/register";
        }
        
        
        //Deel 2 wijzigingen opslaan in DB.
        System.out.println("Gegevens worden opgeslagen in DB");
        userService.addUser(nieuweUser);
       
        List<CartSubOrder> subs = new ArrayList<>();
        
        Cart newCart = new Cart();
       
        newCart.setSubOrders(subs);
        
        newCart.setUser(nieuweUser);
       
        newCart.setId(nieuweUser.getId());
        
        cartService.createCart(newCart);
        
        System.out.println("tot hier gaat het nog goed-Einde DB opslaan.");
            
        return "redirect:/login";
    }

}
