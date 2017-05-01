package rsvier.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/users")
	public ModelAndView getAllUsers(){
		return new ModelAndView("users", "users", userService.getAllUsers());
	}

	@RequestMapping("/users/{id}")
	public ModelAndView getUser(@PathVariable Long id){
		return new ModelAndView("user", "user", userService.getUser(id));
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/users/create")
	public void addUser(@RequestBody User user, UserRole userRole){
	 	userService.addUser(user, userRole);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value = "/users/edit")
	public void updateUser(@RequestBody User user){
	 userService.updateUser(user);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/users/{id}")
	public void deleteUser(@RequestBody Long id){
	 userService.deleteUser(id);
	}

}
