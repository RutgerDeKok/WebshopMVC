/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsvier;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rsvier.address.Address;
import rsvier.address.AddressService;
import rsvier.cart.Cart;
import rsvier.cart.CartService;
import rsvier.cartsuborder.CartSubOrder;

import rsvier.infrastructure.PassHasher;
import rsvier.infrastructure.Validator;
import rsvier.user.User;
import rsvier.user.UserService;
/**
 *
 * @author Frank
 */


















@Controller
public class RegisteerController {
 
    
    @Autowired
	private CartService cartService;
    @Autowired
        private UserService userService;
    @Autowired
        private AddressService addressService;
   // @Autowired
   //         private PassHasher passhasher;
    
    
    
    @RequestMapping(value = { "/registeren" })
	public String inlog() {
		return "registeren";
	}
    
    @RequestMapping(value = "/MaakAccount/ok", method = RequestMethod.POST)
	public String voegAccountToe(HttpServletRequest request) {
		
		System.out.println("Account gegevens worden verwerkt");
                
                
                //Belangrijke zin.
		User NieuweUser = (User) request.getSession().getAttribute("user");
		
                
		NieuweUser.setEmail(request.getParameter("email"));
                
                String wachtwoordstring = request.getParameter("W8");
                //wachtwoord is nog een tekst, verander naar chars.
                char[] wachtwoordChars = wachtwoordstring.toCharArray();
                //Laat wachtwoord zien (check) in system out.
                System.out.println("Wachtwoord is: "+ wachtwoordstring);
                
                
             //   byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(16);
              
             // String OpgeslagenWachtwoord = passhasher.hash(wachtwoordChars,salt);
                
                
                
            //   NieuweUser.setPassHash(request.getParameter("passHash"));
                 NieuweUser.setPassHash(wachtwoordstring);
		
		//nieuwe user heeft nu een wachtwoord.
		
                
                
                //Deel 2 DB.
                
		// dan wijzigingen opslaan in DB (cart of user)
                
                //deze if is niet nodig toch?
		//if(request.getSession().getAttribute("currentUser")!=null){
                
			System.out.println("Gegevens worden opgeslagen in DB");
			userService.updateUser(NieuweUser);
                        
                //einde onnodige if denk ik.     
		//}
                
                
                
                
		/* nu  is aangepast kan de referentie "address" in de sessie
		 weg. Het addresss is immers ook al gerefereerd in de cart (delivery) of user
		 (billing)*/
		
		request.getSession().removeAttribute("user");
		
                
                
                //dit hieronder snap ik niet van adres.
                
		String origin = (String) request.getSession().getAttribute("origin");
		if(origin.equals("checkout")){
			return "redirect:/checkout";
		}else if(origin.equals("registeren")){
			return "redirect:/registeren";
		}
		
		return "redirect:/products";
	}
        
        
        
        @RequestMapping(value = "/MaakAdresNieuw/ok", method = RequestMethod.POST)
	public String voegAdrestoe(HttpServletRequest request) {
		
		System.out.println("Adres gegevens worden verwerkt");
		Address updateAddress = (Address) request.getSession().getAttribute("address");
		
		updateAddress.setFirstName(request.getParameter("firstName"));
		updateAddress.setFamilyName(request.getParameter("lastName"));
		updateAddress.setInsertion(request.getParameter("insertion"));
		updateAddress.setCity(request.getParameter("city"));
		updateAddress.setNumber(Integer.parseInt(request.getParameter("number")));
		updateAddress.setNumAddition(request.getParameter("addition"));
		updateAddress.setStreet(request.getParameter("street"));
		updateAddress.setZipCode(request.getParameter("zipCode"));
		
		//als het een bestaan adress is van een ingelogde user 
		// dan wijzigingen opslaan in DB (cart of user)
		if(request.getSession().getAttribute("currentUser")!=null){
			System.out.println("Gegevens worden opgeslagen in DB");
			addressService.updateAddress(updateAddress);
		}
		/* nu het adres is aangepast kan de referentie "address" in de sessie
		 weg. Het addresss is immers ook al gerefereerd in de cart (delivery) of user
		 (billing)*/
		
		request.getSession().removeAttribute("address");
		
		String origin = (String) request.getSession().getAttribute("origin");
		if(origin.equals("checkout")){
			return "redirect:/checkout";
		}else if(origin.equals("registeren")){
			return "redirect:/registeren";
		}
		
		return "redirect:/products";
	}
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        private void checkForSessionCart(HttpSession session, User user) {
		// checken of er all een cart in de sessie is, 
		Cart sessionCart = (Cart) session.getAttribute("cart");
		if(sessionCart!=null){
			
			//in dat geval moeten de suborders 
			//	worden toegevoegd aan de cart uit de DB.
			Cart dbCart = cartService.getCartByUser(user);
			System.out.println("DB cart id is: "+ dbCart.getId());
			for(CartSubOrder sub:sessionCart.getSubOrders()){
				dbCart.getSubOrders().add(sub);
			}
			
			// is er een recent ingevuld delivery adres in the sessie cart
			// so ja dan die overzetten in de dbCart
			if(sessionCart.getDeliveryAdress().getFirstName()!=null){
			
				// zorgen dat het cart id behouden blijft
				Address sessionAddress = sessionCart.getDeliveryAdress();
				Address dbAddress = dbCart.getDeliveryAdress();

				dbAddress.setCity(sessionAddress.getCity());
				dbAddress.setFamilyName(sessionAddress.getFamilyName());
				dbAddress.setFirstName(sessionAddress.getFirstName());
				dbAddress.setInsertion(sessionAddress.getInsertion());
				dbAddress.setNumAddition(sessionAddress.getNumAddition());
				dbAddress.setNumber(sessionAddress.getNumber());
				dbAddress.setStreet(sessionAddress.getStreet());
				dbAddress.setZipCode(sessionAddress.getZipCode());
			}
			// dbCart in sessie zetten en opslaan in DB
			session.setAttribute("cart", dbCart);	
			cartService.updateCart(dbCart);
		}
		
	}
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    
}
