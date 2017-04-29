package rsvier.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rsvier.product.Product;
import rsvier.product.ProductCategory;

@Controller
public class UserController {

	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value =  { "/employees" })
	public String showProductList( HttpServletRequest request) {
		
//		System.out.println(((User)request.getSession().getAttribute("currentUser")).getEmail() +" gaat naar medewerker pagina");

		return "employees";
	}
	
	
//	@RequestMapping("/users")
//	public List<User> getAllUsers(){
//		return userService.getAllUsers();
//	}


//	@RequestMapping("/users/{id}")
//	public User getUser(@PathVariable Long id){
//		return userService.getUser(id);
//	}
//	
//	@RequestMapping(method=RequestMethod.POST, value = "/users")
//	public void addUser(@RequestBody User user){
//	 userService.addUser(user);
//	}
//	
//	@RequestMapping(method=RequestMethod.PUT, value = "/users")
//	public void updateUser(@RequestBody User user){
//	 userService.updateUser(user);
//	}
//	
//	@RequestMapping(method=RequestMethod.DELETE, value = "/users/{id}")
//	public void deleteUser(@RequestBody Long id){
//	 userService.deleteUser(id);
//	}
	

}
