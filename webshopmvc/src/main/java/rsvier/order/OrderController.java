package rsvier.order;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rsvier.finalsuborder.FinalSubOrder;





@Controller
public class OrderController {
	
	@Autowired
	private  OrderService orderService;
	


	@RequestMapping(value =  { "/employees/orders" })
	public String showOrderList(HttpSession session) {
		
		List<Order> orders = orderService.getAllOrders();
		for(Order order:orders){
			order.setSaledate(LocalDate.now());
			orderService.updateOrder(order);
		}
		
		session.setAttribute("orders", orders);
		return "orders";
	}
	
	
	@RequestMapping(value = "/employees/orders/view", method = RequestMethod.GET)
	public String gotoOrderEdit() {
		
		return "Order_view";
	}
	
	@RequestMapping(value = "employees/orders/view", method = RequestMethod.POST)
	public @ResponseBody void updateOrder(@RequestParam("index") String index, 
			Map<String, Object> model, HttpSession session, HttpServletResponse response){
		@SuppressWarnings("unchecked")
		List<Order> orders = (ArrayList<Order>) session.getAttribute("orders");
		
		
		int orderIndex = (Integer.parseInt(index));
		
		Order order = orders.get(orderIndex);
		List<FinalSubOrder> subs = orderService.findSubOrders(order.getId());
		
		for(FinalSubOrder sub:subs){
			System.out.println("subOrder id = "+sub.getPrd_Name());
			System.out.println("subOrder id = "+sub.getQuantity());
		}
		
		model.put("order", order);
		model.put("subs", subs);
		
		try {
			response.sendRedirect("/order_view");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	



}
