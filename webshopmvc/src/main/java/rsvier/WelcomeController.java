package rsvier;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import rsvier.cart.AnonymousCart;
import rsvier.cart.Cart;
import rsvier.cart.CartInterface;
import rsvier.cart.CartService;
import rsvier.product.Product;
import rsvier.product.ProductService;
import rsvier.user.User;


@Controller
class WelcomeController {

    @Autowired
    private ProductService productService;
    @Autowired
	CartService cartService;
    @Autowired
	private CartInterface cart;


	@RequestMapping(value ="/")
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
	}

    @RequestMapping("/boerPiet")
	public String contact() {
		return "BoerPiet";
	}

    @RequestMapping("/Kaas")
	public String Keuze() {
		return "KaasKeuzeMenu";
	}
        
    @RequestMapping("/wijzigAdres")
	public String AddressThing() {
		return "wijzigAdres";
	}


}

