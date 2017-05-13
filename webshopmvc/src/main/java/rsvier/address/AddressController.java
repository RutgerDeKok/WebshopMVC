package rsvier.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import rsvier.cart.Cart;
import rsvier.cartsuborder.CartSubOrderService;
import rsvier.product.ProductService;
import rsvier.user.User;
import rsvier.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
	

	@RequestMapping(value = "/wijzig-adres", method = RequestMethod.GET)
	public @ResponseBody void updateAddress(@RequestParam("address") String addressType,
			@RequestParam("origin") String origin, HttpSession session, HttpServletResponse response) {

		/*
		 * Het address (delivery of billing) welke dan ook, dat is meegegeven
		 * aan deze methode krijgt een tijdelijke referentie in de sessie als
		 * "address"
		 */

		System.out.println("Origin of request is: " + origin);
		session.setAttribute("origin", origin);
		System.out.println("Address type is: " + addressType);
		if (addressType.equals("delivery")) {
			session.setAttribute("address", ((Cart) session.getAttribute("cart")).getDeliveryAddress());
		} else if (addressType.equals("billing")) {
			session.setAttribute("address", ((User) session.getAttribute("currentUser")).getBillingAddress());
		} 
		
		
		try {
			response.sendRedirect("/maak-adres");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}



	@RequestMapping(value = "/copy_adres", method = RequestMethod.GET)
	public String copyAdres(HttpSession session) {
		checkAddressInCart(session);
		Cart cart = (Cart) session.getAttribute("cart");
		Address billingAdd = ((User) session.getAttribute("currentUser")).getBillingAddress();
		Address deliveryAdd = cart.getDeliveryAddress();

		deliveryAdd.setCity(billingAdd.getCity());
		deliveryAdd.setFamilyName(billingAdd.getFamilyName());
		deliveryAdd.setFirstName(billingAdd.getFirstName());
		deliveryAdd.setInsertion(billingAdd.getInsertion());
		deliveryAdd.setNumAddition(billingAdd.getNumAddition());
		deliveryAdd.setNumber(billingAdd.getNumber());
		deliveryAdd.setStreet(billingAdd.getStreet());
		deliveryAdd.setZipCode(billingAdd.getZipCode());
		
		addressService.updateAddress(deliveryAdd);

		session.setAttribute("cart", cart);

		return "/checkout";
	}

	private void checkAddressInCart(HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart.getDeliveryAddress() == null) {
			cart.setDeliveryAddress(new Address());
			//niet nodig, zit al in de sessie
//			session.setAttribute("cart", cart);
		}
	}


	@RequestMapping(value = "/maak-adres/ok", method = RequestMethod.POST)
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
		else {
			((Cart)request.getSession().getAttribute("cart")).setDeliveryAddress(updateAddress);
			System.out.println("Delivery adres: " + ((Cart)request.getSession().getAttribute("cart")).getDeliveryAddress().toString());
		}
		/* nu het adres is aangepast kan de referentie "address" in de sessie
		 weg. Het addresss is immers ook al gerefereerd in de cart (delivery) of user
		 (billing)*/
		
		request.getSession().removeAttribute("address");
		
		String origin = (String) request.getSession().getAttribute("origin");
		if(origin.equals("checkout")){
			return "redirect:/checkout";
		}else if(origin.equals("register")){
			return "redirect:/register";
		}
		
		return "redirect:/products";
	}
	

	@RequestMapping(value = "/maak-adres", method = RequestMethod.GET)
	public String gaNaarMaakAdress() {
		return "maak_adres";
	}

}