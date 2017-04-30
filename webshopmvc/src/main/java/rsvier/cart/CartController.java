package rsvier.cart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rsvier.cartsuborder.CartSubOrder;
import rsvier.cartsuborder.CartSubOrderService;
import rsvier.product.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import rsvier.product.ProductService;
import rsvier.address.Address;
import rsvier.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class CartController {

	@Autowired
	ProductService productService;
	@Autowired
	CartSubOrderService cartSubOrderService;
	@Autowired
	CartService cartService;

	@RequestMapping (value =  { "/cart" })
	public String shoppingCart(HttpServletRequest request) {

		checkForCart(request.getSession());
		return "cart";
	}

	@RequestMapping(value = "/cart/delete", method = RequestMethod.GET)
	public String removeFromCart(@RequestParam int subOrderIndex, HttpServletRequest request) {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		// verwijderen op basis van de index in de tabel
		// is gelijk aan index in ArrayList<CartSubOrder>
		cart.getSubOrders().remove(subOrderIndex);
		cart.calculateTotalPrice();
		request.getSession().setAttribute("cart", cart);
		return "redirect:/cart";
	}

	@RequestMapping(value = "cart/add", method = RequestMethod.POST)
	public @ResponseBody void addProductToCart(@RequestParam("choice") String[] choice, HttpServletRequest request,
			HttpServletResponse response) {

		@SuppressWarnings("unchecked")
		List<Product> lijst = (ArrayList<Product>) request.getSession().getAttribute("lijst");
		int prodIndex = (Integer.parseInt(choice[1]));
		int quantity = (Integer.parseInt(choice[0]));
		System.out.println("prodIndex is: " + prodIndex);
		System.out.println("aantal is: " + quantity);

		Product chosenProduct = lijst.get(prodIndex);

		// geen id aan suborder meegeven, dat doet jpa
		CartSubOrder cso = new CartSubOrder(chosenProduct, quantity);
//		cso.calculateSubTotal(); done in constructor

		checkForCart(request.getSession());
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.getSubOrders().add(cso);
		cart.calculateTotalPrice();
		request.getSession().setAttribute("cart", cart);
		try {
			response.sendRedirect("/cart");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	

	private void checkForCart(HttpSession session) {
		Cart cart;
		// is er nog geen cart in de sessie?
		if (session.getAttribute("cart") == null) {
			// is er dan misschien wel al een user ingelogd?
			User user = (User) session.getAttribute("currentUser");
			if (user != null) {
				// in dat geval haal de user cart uit de DB
				cart = cartService.getCart(user.getId());
				if(cart.getSubOrders()==null){
					System.out.println("From cartCheck method in CartController Cart subOrders == null, creating new ArrayList");
					cart.setSubOrders(new ArrayList<CartSubOrder>());
				}
			} else {
				// geen cart en geen ingelogde user, dan cart maken
				cart = new Cart();
				// en lege lijst suborders aan koppelen
				cart.setSubOrders(new ArrayList<CartSubOrder>());
			}
			// nu de cart opslaan in de sessie
			cart.calculateTotalPrice();
			System.out.println("cart total price is: "+cart.getTotalPrice());
			session.setAttribute("cart", cart);
		}
	}

	@RequestMapping("/checkout")
	public String checkout(HttpServletRequest request) {
		
		Address address;
		
		User user = (User) request.getSession().getAttribute("currentUser");
		if(user==null){   // nog niemand ingelogd, dan nieuw adress maken.
			address = new Address();
		} else {
			address = user.getBillingAddress();
		}
		request.getSession().setAttribute("address", address);
		return "Checkout";
	}


}