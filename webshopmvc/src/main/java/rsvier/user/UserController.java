package rsvier.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rsvier.address.Address;


@Controller
public class UserController {

	
	@Autowired
	private UserService userService;
	private UserType typeFilter;
	
	
	
	@RequestMapping(value =  { "/employees/users" }, method = RequestMethod.GET)
	public String showUsersEmployees(Map<String, Object> model, HttpServletRequest request) {
		
		putUsersInSession(request.getSession());
		model.put("types", UserType.values());
		return "emp_users";
	}
	
	private void putUsersInSession(HttpSession session) {
		System.out.println("running put products in session");
		
		typeFilter= (UserType) session.getAttribute("typeFilter");
		
		if(typeFilter ==null){
			typeFilter = UserType.ALL;
		}

		List<User> users = userService.getUsersByType(typeFilter);;
		session.setAttribute("users", users);
		session.setAttribute("typeFilter", typeFilter);
	}

	@RequestMapping(value = "employees/users/filter", method = RequestMethod.POST)
	public @ResponseBody void filterUsersEdit(@RequestParam("filter") String radioWaarde,  HttpServletRequest request, HttpServletResponse response) {		
			System.out.println("radiowaarde is: "+radioWaarde);
		UserType typeFilter = UserType.valueOf(radioWaarde);
		request.getSession().setAttribute("typeFilter", typeFilter);

		try {
			response.sendRedirect("/employees/users");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@RequestMapping(value = "employees/users/delete", method = RequestMethod.POST)
	public @ResponseBody void deleteUSers(@RequestParam("delindex") String choice, HttpServletRequest request, HttpServletResponse response) {		

		System.out.println("delete user methode");
		@SuppressWarnings("unchecked")
		List<User> users = (ArrayList<User>) request.getSession().getAttribute("users");
		int userIndex = (Integer.parseInt(choice));
		System.out.println("usersIndex is: " + userIndex);
		
		User user = (User)users.get(userIndex);
		System.out.println("product to be deleted id: "+user.getId()+" email: "+user.getEmail());
		try {  // cannot delete a product if it is used as a key inside a suborder
		userService.deleteUser(user.getId());
		users.remove(userIndex);
		} catch (Exception e){
			System.out.println(e.getMessage());
//			putProductsInSession(request.getSession());
		}
		try {
			response.sendRedirect("/employees/users");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@RequestMapping(value = "employees/users/new", method = RequestMethod.GET)
	public @ResponseBody void newUser(HttpSession session, HttpServletResponse response){
			
		User user = new User();
		//om null pointer in het pull down menu te voorkomen
		// enum waarde toevoegen
		user.setUserType(UserType.values()[1]);
		
		session.setAttribute("newUser", user);
		
		try {
			response.sendRedirect("/user_new");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
        
        @RequestMapping(value = "employees/users/user_edit", method = RequestMethod.POST)
	public @ResponseBody void editUser(HttpSession session, HttpServletResponse response){
        
	
            
            
            
            

}
        
         @RequestMapping(value = "/user_new", method = RequestMethod.GET)
	public String UserNEW(HttpSession session, HttpServletResponse response){
        
       
    
//        Eerste keer een leeg adres
        if (session.getAttribute("tempAdres") == null) {

            Address nieuweAdres = new Address();
            session.setAttribute("tempAdres", nieuweAdres);
        }
        return "user_new";
        
        }
}
