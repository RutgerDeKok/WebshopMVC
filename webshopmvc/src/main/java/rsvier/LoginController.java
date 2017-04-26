package rsvier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import rsvier.infrastructure.PassHasher;
import rsvier.infrastructure.Validator;
import rsvier.user.User;
import rsvier.user.UserService;

/**
 *
 * @author Frank
 */

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	private User currentUser;

	// @RequestMapping("/")
	// public String welcome() {
	// return "welcome";
	// }

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody void loginCheck(HttpServletRequest request, HttpServletResponse response) {
		String uname = request.getParameter("uname");
		String pass = request.getParameter("psw");

		char[] passChars = pass.toCharArray();
//                System.out.println(pass);
//                System.out.println(passChars.toString());
		User user = null;

		// get user by email
		try {

			Validator validator = Validator.getInstance();
			if (validator.validateEmail(uname)) {
                            System.out.println("valid email");
				user = userService.findUserByEmail(uname);
			} else {
				System.out.println("Not a valid user");
			}

			// check password
//                        System.out.println(user.getId());
//                        System.out.println(user.getPassHash());
                       
			if (PassHasher.check(passChars, user.getPassHash())) {
				System.out.println("login succesful!");
				// current user set
                                request.getSession().setAttribute("currentUser", user);
				
			} else {
				System.out.println("login incorrect!");
			}
			
//			System.out.println("user email" + user.getEmail());
//			System.out.println("user id" + user.getId());
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
                        
		
		}

		//
		// try {
		// response.sendRedirect("/products");
		// } catch (IOException e) {
		//
		// e.printStackTrace();
		// }
	}

//	private final UserService UserService;
//
//	@Autowired
//	public LoginController(UserService iets) {
//		this.UserService = iets;
//	}

	@RequestMapping(value = { "/login" })
	public String inlog() {
		return "login";
	}
	
	public User getCurrentUser(HttpServletRequest request) {
            return (User) request.getSession().getAttribute("currentUser");
		
	}


}
