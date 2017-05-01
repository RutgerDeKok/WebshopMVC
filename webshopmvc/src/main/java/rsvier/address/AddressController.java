package rsvier.address;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rsvier.cartsuborder.CartSubOrderService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import rsvier.address.Address;
import rsvier.product.ProductService;
import rsvier.address.AddressService;
import rsvier.cart.Cart;
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

	@RequestMapping(value = "/wijzigAdres", method = RequestMethod.GET)
	public @ResponseBody void updateAddress(@RequestParam("address") String addressType,
			@RequestParam("origin") String origin, HttpSession session, HttpServletResponse response) {

		/*
		 * Het address (delivery of billing) welke dan ook, dat is meegegeven
		 * aan deze methode krijgt een tijdelijke referentie in de sessie als
		 * "address"
		 */

		System.out.println("Origin of request is: " + origin);
		System.out.println("Address type is: " + addressType);
		if (addressType.equals("delivery")) {
			session.setAttribute("address", ((Cart) session.getAttribute("cart")).getDeliveryAdress());
		} else if (addressType.equals("billing")) {
			session.setAttribute("address", ((User) session.getAttribute("currentUser")).getBillingAddress());
		} 
		
//		return "maakAdres";
		
		try {
			response.sendRedirect("/MaakAdres");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	// @RequestMapping(value="/wijzigAdres", method = RequestMethod.GET)
	// public String wijzigAdres() {
	// return "wijzigAdres";
	// }

	@RequestMapping(value = "/copyAdres", method = RequestMethod.GET)
	public String copyAdres(HttpSession session) {
		checkAddressInCart(session);
		Cart cart = (Cart) session.getAttribute("cart");
		Address billingyAdd = ((User) session.getAttribute("currentUser")).getBillingAddress();
		Address deliveryAdd = cart.getDeliveryAdress();

		deliveryAdd.setCity(billingyAdd.getCity());
		deliveryAdd.setFamilyName(billingyAdd.getFamilyName());
		deliveryAdd.setFirstName(billingyAdd.getFirstName());
		deliveryAdd.setInsertion(billingyAdd.getInsertion());
		deliveryAdd.setNumAddition(billingyAdd.getNumAddition());
		deliveryAdd.setNumber(billingyAdd.getNumber());
		deliveryAdd.setStreet(billingyAdd.getStreet());
		deliveryAdd.setZipCode(billingyAdd.getZipCode());
		
		addressService.updateAddress(deliveryAdd);

		session.setAttribute("cart", cart);

		return "/checkout";
	}

	private void checkAddressInCart(HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart.getDeliveryAdress() == null) {
			cart.setDeliveryAdress(new Address());
			session.setAttribute("cart", cart);
		}
	}


	@RequestMapping(value = "/MaakAdres/ok", method = RequestMethod.POST)
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
		return "redirect:/checkout";
	}

	@RequestMapping(value = "/MaakAdres", method = RequestMethod.GET)
	public String gaNaarMaakAdress() {
		return "MaakAdres";
	}

}