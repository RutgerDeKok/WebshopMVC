package rsvier.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.core.net.SyslogOutputStream;
import rsvier.cartsuborder.CartSubOrder;
import rsvier.cartsuborder.CartSubOrderService;
import rsvier.product.Product;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import rsvier.address.Address;
import rsvier.product.ProductService;
import rsvier.address.AddressService;
import rsvier.cart.Cart;
import rsvier.cart.CartService;
import rsvier.finalsuborder.FinalSubOrder;
import rsvier.finalsuborder.FinalSubOrderService;
import rsvier.user.User;
import rsvier.user.UserService;

@Controller
public class OrderController {

	@Autowired
	CartService cartService;
	@Autowired
	ProductService productService;
	@Autowired
	CartSubOrderService cartSubOrderService;
	@Autowired
	UserService userService;
	@Autowired
	AddressService addressService;
	@Autowired
	FinalSubOrderService finalSubOrderService;
	@Autowired
	OrderService orderService;
	

	@RequestMapping("/confirm")
	public String confirmOrder(Model model, HttpServletRequest request, HttpSession session) {

		Cart cart = (Cart) session.getAttribute("cart");
		User user = (User) session.getAttribute("currentUser");
		Address address = cart.getDeliveryAdress();
	

		Order order = new Order();
		List<FinalSubOrder> finalSubOrderList = new ArrayList<>();
		order.setSubOrder(finalSubOrderList);
		
		// CartSubOrder naar FinalSubOrders
		List<CartSubOrder> subOrderList = cart.getSubOrders();
		for (CartSubOrder subOrder : subOrderList) {
			
			finalSubOrderList.add(new FinalSubOrder(subOrder));

			// Stock aanpassen
			Product product = subOrder.getProduct();

			product.setStockCount(product.getStockCount() - subOrder.getQuantity());
			productService.updateProduct(product);

		}
		

		// Order details 
		order.setDeliveryAdress(address);
		order.setSaledate(new Date());
		order.setTotalPrice(cart.getTotalPrice());
		//user mag null zijn bij anoniem
		order.setUser(user);
		System.out.println(order);
		orderService.createOrder(order);
		
		// cart leegmaken en opslaan
		cart.getSubOrders().clear();
		cart.setTotalPrice(new BigDecimal("0"));
		if(cart.getId()>0){
		cartService.updateCart(cart);
		}
		
		String date = new SimpleDateFormat("dd MMM yyyy").format(order.getSaledate());
		
//		String datetime = new SimpleDateFormat("dd-MMM-yyyy  hh.mm").format(order.getSaledate());
		
		model.addAttribute("date", date);
		model.addAttribute("address", address);
		model.addAttribute("order", order);
		model.addAttribute("user", user);
		
		
		productService.clearLocalList();
		// hierdoor ververst de product List bij het producten overzicht
		
		return "confirm";

	}
	
	
	

	@RequestMapping(value = { "/employees/orders" })
	public String showOrderList(HttpSession session) {

		
		List<Order> orders = orderService.getAllOrders();

		session.setAttribute("orders", orders);
		return "orders";
	}
	
	
	

	@RequestMapping(value = "/employees/orders/view", method = RequestMethod.GET)
	public String gotoOrderEdit() {

		return "order_details";
	}
	
	
	

	@RequestMapping(value = "employees/orders/view", method = RequestMethod.POST)
	public @ResponseBody void updateOrder(@RequestParam("index") String index,
			HttpSession session, HttpServletResponse response) {
		@SuppressWarnings("unchecked")
		List<Order> orders = (ArrayList<Order>) session.getAttribute("orders");

		int orderIndex = (Integer.parseInt(index));

		Order order = orders.get(orderIndex);

		List<FinalSubOrder> subs = orderService.findSubOrders(order.getId());
		for(FinalSubOrder sub:subs){
			System.out.println(sub);
		}
		
		String datetime = new SimpleDateFormat("dd-MMM-yyyy  hh.mm").format(order.getSaledate());
		
		session.setAttribute("date", datetime);

		session.setAttribute("order", order);
		session.setAttribute("subs", subs);
		
	

		try {
			response.sendRedirect("/employees/orders/view");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	/*
	    Oude versie gebruikt billing address ipv delivery address, anonnieme gebruikers hebben 
	    geen billing address, dus handiger om op de order het aflever adres te zetten. 
	    Misschien zouden beide op de order moeten komen in een echt scenario
	    FinalSuborders worden apart opgeslagen, dit hoeft niet,
	    Cart subOrders worden individueel weggehaald, dit hoeft niet, gewoon de List in Cart clearen.
	    
	 
	  @RequestMapping("/confirm")
    public String confirmOrder(Model model, HttpServletRequest request, HttpSession session) {
  
        Cart cart=  (Cart)session.getAttribute("cart");
        User user = (User)session.getAttribute("currentUser");
        Address address;
        
        if (user.getBillingAddress()==null)
            address= new Address();
        else
            address = user.getBillingAddress();
        
        Order order = new Order();
        FinalSubOrder finalSubOrder; 
        List<FinalSubOrder> finalSubOrderList= new ArrayList<>();
        
        
        // CartSubOrder naar FinalSubOrders 
        List<CartSubOrder> subOrderList = cart.getSubOrders();
        for (CartSubOrder subOrder: subOrderList ){
            finalSubOrder = new FinalSubOrder();
            finalSubOrder.setPrd_name(subOrder.getProduct().getName());
            finalSubOrder.setItem_price(subOrder.getProduct().getPrice());
            finalSubOrder.setPrd_brand(subOrder.getProduct().getBrand());
            finalSubOrder.setPrd_category(subOrder.getProduct().getCategory());
            finalSubOrder.setQuantity(subOrder.getQuantity());
            finalSubOrder.setSubTotal(subOrder.getSubTotal());
 
          
            //Stock aanpassen
            Product product = subOrder.getProduct();
            System.out.println(finalSubOrder.getId());
            System.out.println(finalSubOrder.getPrd_name());
            System.out.println(product.getName());
           
            product.setStockCount(product.getStockCount() - finalSubOrder.getQuantity());
            productService.updateProduct(product);
            //FinalSuborder toevoegen in db
            finalSubOrderService.createFinalSubOrder(finalSubOrder);
            //deze toevoegen aan de order
            order.addSubOrder(finalSubOrder);
            //CartSubOrders Verwijderen
            cartSubOrderService.deleteCartSubOrder(subOrder);
            //Cart leegmaken
            //cartService.removeFromCart(cart, subOrder);
          
        }
            
         // Cart naar Order en Cart leegmaken  
         order.setDeliveryAdress(address);
         order.setSaledate(new Date());
         order.setTotalPrice(cart.getTotalPrice());
         order.setUser(user);
         orderService.createOrder(order);
         
         
         // Alleen de totalPrice moet nu nog verwijderd worden, cart met adres blijft
         cart.setTotalPrice(new BigDecimal("0"));
         cartService.updateCart(cart);

        model.addAttribute("address", address);
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        
        return "confirm";
        
         // als een gebruiker is ingelogd, de gebruiker
  // opnieuw opslaan in de dB ,cascade moett er voor zorgen dat wijzigingen 
  // in cart en user (zoals addressen) worden oopgeslagen
 
        
    }
   
}
	 */
	
	
	

}