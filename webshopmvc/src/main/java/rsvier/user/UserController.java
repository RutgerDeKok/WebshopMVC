package rsvier.user;

import org.springframework.beans.factory.annotation.Autowired;

//@Controller
public class UserController {

	
	@Autowired
	private UserService userService;
	
	
	
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
