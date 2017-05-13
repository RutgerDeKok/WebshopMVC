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

import rsvier.cartsuborder.CartSubOrder;
import rsvier.cartsuborder.CartSubOrderService;
import rsvier.product.Product;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
		Address address = cart.getDeliveryAddress();

		Order order = new Order();
		
		// CartSubOrder naar FinalSubOrders
		List<CartSubOrder> subOrderList = cart.getSubOrders();
		for (CartSubOrder subOrder : subOrderList) {
			
			order.getSubOrders().add(new FinalSubOrder(subOrder));

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
		session.setAttribute("order", order);
		
		
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
	
	
	@RequestMapping(value = { "/orders" })
	public String showCustommerOrders(HttpSession session) {
		System.out.println("Show customer orders");

		
		List<Order> myOrders = orderService.getOrdersByUser((User)session.getAttribute("currentUser"));

		session.setAttribute("myOrders", myOrders);
		return "customer_orders";
	}
	
	
	

	@RequestMapping(value = "/employees/orders/view", method = RequestMethod.GET)
	public String gotoOrderEdit(Model model) {
		model.addAttribute("back","employee");
		System.out.println("back is employee");
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
	
	
	@RequestMapping(value = "/orders/view", method = RequestMethod.GET)
	public String gotoOrderView(Model model) {
		model.addAttribute("back","customer");
		System.out.println("back is customer");
		return "order_details";
	}
	
	
	

	@RequestMapping(value = "/orders/view", method = RequestMethod.POST)
	public @ResponseBody void viewOrder(@RequestParam("index") String index,
			HttpSession session, HttpServletResponse response) {
		@SuppressWarnings("unchecked")
		List<Order> orders = (ArrayList<Order>) session.getAttribute("myOrders");

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
			response.sendRedirect("/orders/view");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	

}