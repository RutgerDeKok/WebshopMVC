package rsvier;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import rsvier.cart.Cart;
import rsvier.cart.CartService;
import rsvier.product.Product;
import rsvier.product.ProductService;

@Controller
class WelcomeController {

    @Autowired
    private ProductService productService;
    @Autowired
	CartService cartService;
    @Autowired
	private Cart cart;


	@RequestMapping(value ="/")

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
	
	public String welcome(Model model, HttpServletRequest request) {
        List<Product> list = productService.getAllProducts();
//        User test = (User)request.getSession().getAttribute("currentUser");
//        if(test==null){
//        	User dummyUser = new User();
//        	Address adress = new Address();
//        	
//        	Cart cart = new Cart();
//        	cart.setId(0);
//        	cart.setUser(dummyUser);
//        	dummyUser.setId(0);
//        	request.getSession().setAttribute("currentUser",dummyUser);
//        	System.out.println("no test user, dummy created");
//        	
//        }else{
//        System.out.println("naam van test user is: "+test.getEmail());
//        }
    
        
		model.addAttribute("productlist", list);
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

