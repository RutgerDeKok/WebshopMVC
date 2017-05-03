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
import rsvier.infrastructure.Validator;
import rsvier.user.User;
import rsvier.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginCheck(HttpServletRequest request, HttpServletResponse response) {
		

		String uname = request.getParameter("uname");
		String pass = request.getParameter("psw");

		char[] passChars = pass.toCharArray();

		User user = null;

		// get user by email
		try {

			Validator validator = Validator.getInstance();
			if (validator.validateEmail(uname)) {
				user = userService.findUserByEmail(uname);
				System.out.println("User: " +user.toString());
			} else {
				System.out.println("Not a valid user");
			}

			if (PassHasher.check(passChars, user.getPassHash())) {
				System.out.println("login succesful!");
				System.out.println("User: " +user.toString());
				// current user set
				request.getSession().setAttribute("currentUser", user);
				
				
				// checken of er all een cart in de sessie is, in dat geval moeten de suborders 
				//	worden toegevoegd aan de cart uit de DB.
				checkForSessionCart(request.getSession(), user);
		

			} else { // foute inlog
				System.out.println("login incorrect!");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return "success";

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

	@RequestMapping("/logout")
	public void Logout(HttpServletRequest request, HttpServletResponse response) {
		
		request.getSession().removeAttribute("currentUser");
		System.out.println("logging out");

		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String inlog() {
		return "login";
	}

	public User getCurrentUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("currentUser");

	}

}
