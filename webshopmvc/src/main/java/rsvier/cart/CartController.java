package rsvier.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import rsvier.cartsuborder.CartSubOrder;
import rsvier.cartsuborder.CartSubOrderService;
import rsvier.product.Product;
import rsvier.product.ProductCategory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import rsvier.product.ProductService;
import rsvier.address.Address;
import rsvier.user.User;

import javax.annotation.PostConstruct;
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
	@Autowired
	Cart cart;
	@Autowired
	User user;

	public void init(HttpSession session) {
		if (session.getAttribute("cart") == null) {
			if (user.isEnabled()) {
				cart = cartService.getCartByUserId(user.getId());
			} else {
				cart = new Cart(session.getId());
				session.setAttribute("cart", cart);
			}
		} else {
			cart = (Cart) session.getAttribute("cart");
		}
	}

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
		if (choice[0].isEmpty()) {
			choice[0] = "1";
		}
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
//		Product product = new Product(1, "kaas", "merk", ProductCategory.GOAT, "info", (new BigDecimal("2.50")), 200);
//		CartSubOrder cso = new CartSubOrder(1, product, 5);
//		cso.setSubTotal(product.getPrice(), cso.getQuantity());
//		CartSubOrder cso2 = new CartSubOrder(2, product, 3);
//		cso2.setSubTotal(product.getPrice(), cso2.getQuantity());
//		Cart cart = new Cart();
//		cart.addSubOrder(cso);
//		cart.addSubOrder(cso2);
//		cart.calculateTotalPrice();
//		model.addAttribute("cart", cart);
		
		Address address;
		
		User user = (User) request.getSession().getAttribute("currentUser");
		if(user==null){   // nog niemand ingelogd, dan nieuw adress maken.
			// voor test doeleinden nu ingevuld met mock data
			address = new Address(2, "Tjeerd", "van", "Santema", "hiero", 22, "b", "9283 AG", "Groningen");
		} else {
			address = user.getBillingAddress();
		}
		request.getSession().setAttribute("address", address);
		return "Checkout";
	}

}