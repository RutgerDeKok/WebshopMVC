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
import static rsvier.user.UserType.CUSTOMER;

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
    
    
    //opend de pagina registeren van de html.
    @RequestMapping(value = { "/registeren" })
	public String inlog() {
		return "registeren";
	}
        
        
    //Dit moet een account aanmaken.
    @RequestMapping(value = "/MaakAccount/ok", method = RequestMethod.POST)
	public String voegAccountToe(HttpServletRequest request) {
		
		System.out.println("Account gegevens worden verwerkt");
                
                
                		
                User NieuweUser = new User();
                Address NieuweAdres = new Address();
                
                
                String input1 = request.getParameter("email");
                System.out.println(input1);
                String input2 = request.getParameter("W8");
                System.out.println(input2);
                try{
		NieuweUser.setEmail(input1);
                //Het is altijd een klant op deze manier.
                NieuweUser.setUserType(CUSTOMER);
                }
                catch(Exception een){ System.out.println(een.getMessage());}
                
                //wachtwoord is nog een tekst, verander naar chars.
                char[] wachtwoordChars = input2.toCharArray();
                //Laat wachtwoord zien (check) in system out.
                
                System.out.println("tot hier gaat het nog goed.");
               
                
                
             //   byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(16);
              
             // String OpgeslagenWachtwoord = passhasher.hash(wachtwoordChars,salt);
                
                
                
            //   NieuweUser.setPassHash(request.getParameter("passHash"));
            try{
                int i =16;
                byte b =(byte)i;
                byte[] barray = {b};
                String GehashdeWachtwoord = PassHasher.hash(wachtwoordChars,barray);
                
                
                
                 NieuweUser.setPassHash(GehashdeWachtwoord);
		
		//nieuwe user heeft nu een wachtwoord.
		}
                catch(Exception twee){ System.out.println(twee.getMessage()); System.out.println("Het wachtwoord opslaan gaat fout");}
                System.out.println("tot hier gaat het nog goed-2.");
                
//                adres gedeelte invullen.
                System.out.println("Adres gegevens worden verwerkt");
		
		
		NieuweAdres.setFirstName(request.getParameter("firstName"));
		NieuweAdres.setFamilyName(request.getParameter("lastName"));
		NieuweAdres.setInsertion(request.getParameter("insertion"));
		NieuweAdres.setCity(request.getParameter("city"));
		NieuweAdres.setNumber(Integer.parseInt(request.getParameter("number")));
		NieuweAdres.setNumAddition(request.getParameter("addition"));
		NieuweAdres.setStreet(request.getParameter("street"));
		NieuweAdres.setZipCode(request.getParameter("zipCode"));
                
                
                
                
                
                
                
                
                
                
                
                
                //Deel 2 DB.
                
		// dan wijzigingen opslaan in DB (cart of user)
                
                //deze if is niet nodig toch, want er is een nieuwe en niks anders nodig?
		//if(request.getSession().getAttribute("currentUser")!=null){
                
			System.out.println("Gegevens worden opgeslagen in DB");
			userService.addUser(NieuweUser);
                      
                     System.out.println("tot hier gaat het nog goed-3.");   
                        
                        
                //einde onnodige if denk ik.     
		//}
                
                
                
                
		/* nu  is aangepast kan de referentie "address" in de sessie
		 weg. Het addresss is immers ook al gerefereerd in de cart (delivery) of user
		 (billing)*/
		
		request.getSession().removeAttribute("user");
		
                System.out.println("tot hier gaat het nog goed-4.");
                
                //dit hieronder snap ik niet van adres.
                
//		String origin = (String) request.getSession().getAttribute("origin");
//		if(origin.equals("checkout")){
//			return "redirect:/checkout";
//		}else if(origin.equals("registeren")){
//			return "redirect:/registeren";
//		}
		
		return "redirect:/login";
	}
        
        
        //Dit moet een adres maken.
        @RequestMapping(value = "/MaakAdresNieuw/ok", method = RequestMethod.POST)
	public String voegAdrestoe(HttpServletRequest request) {
		
		System.out.println("Adres gegevens worden verwerkt");
		Address NieuweAdres = (Address) request.getSession().getAttribute("address");
		
		NieuweAdres.setFirstName(request.getParameter("firstName"));
		NieuweAdres.setFamilyName(request.getParameter("lastName"));
		NieuweAdres.setInsertion(request.getParameter("insertion"));
		NieuweAdres.setCity(request.getParameter("city"));
		NieuweAdres.setNumber(Integer.parseInt(request.getParameter("number")));
		NieuweAdres.setNumAddition(request.getParameter("addition"));
		NieuweAdres.setStreet(request.getParameter("street"));
		NieuweAdres.setZipCode(request.getParameter("zipCode"));
		
		//als het een bestaan adress is van een ingelogde user 
		// dan wijzigingen opslaan in DB (cart of user)
                //deel 2 DB
                
		if(request.getSession().getAttribute("currentUser")!=null){
			System.out.println("Gegevens worden opgeslagen in DB");
			addressService.createAddress(NieuweAdres);
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
