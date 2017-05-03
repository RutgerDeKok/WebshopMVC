package rsvier;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import rsvier.cart.Cart;
import rsvier.cart.CartService;
import rsvier.product.ProductService;


@Controller
class WelcomeController {

    @Autowired
    private ProductService productService;
    @Autowired
	CartService cartService;
    @Autowired
	private Cart cart;


	

	/*  cookie methode jurjen
	public String welcome(@CookieValue(value="sessionId", required = false) String sessionId, Model model, HttpSession session, HttpServletResponse response) {
		System.out.println(sessionId);
		if (sessionId == null || sessionId.isEmpty()) {
			System.out.println("Geen cookie gevonden!");
			response.addCookie(new Cookie("sessionId", session.getId())); // sessionId nog hashen?
			cart = new AnonymousCart(session.getId());
		} else {
			cart = cartService.getCartBySessionId(sessionId);
		}
		System.out.println(cart.toString());
		List<Product> list = productService.getAllProducts();
        return "welcome";
		*/
    @RequestMapping(value ="/")
	public void welcome(HttpServletResponse response) {
		
		
//		// Dummy medewerker toevoegen om makkelijk medewerker menu's te testen
//			User employee = new User();
//			employee.setEmail("test_employee@rs.nl");
//			employee.setUserType(UserType.EMPLOYEE);
//			request.getSession().setAttribute("currentUser", employee);
        
        
    	try {
			response.sendRedirect("/products");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

    @RequestMapping("/boerPiet")
	public String contact() {
		return "BoerPiet";
	}

    @RequestMapping("/Kaas")
	public String Keuze() {
		return "KaasKeuzeMenu";
	}
        



}


