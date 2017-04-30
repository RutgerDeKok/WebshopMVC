package rsvier;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rsvier.infrastructure.PassHasher;
import rsvier.infrastructure.Validator;
import rsvier.user.User;
import rsvier.user.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginCheck(HttpServletRequest request, HttpServletResponse response) {

		String uname = request.getParameter("uname");
		String pass = request.getParameter("psw");

		char[] passChars = pass.toCharArray();

		User user = null;

		// get user by email
		try {

			Validator validator = Validator.getInstance();
			if (validator.validateEmail(uname)) {
				user = userService.findUserByEmail(uname);
			} else {
				System.out.println("Not a valid user");
			}

			if (PassHasher.check(passChars, user.getPassHash())) {
				System.out.println("login succesful!");
				// current user set
				request.getSession().setAttribute("currentUser", user);

			} else {
				System.out.println("login incorrect!");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return "success";

	}

	@RequestMapping("/logout")
	public void Logout(HttpServletRequest request, HttpServletResponse response) {
		User anonymus = new User();
		request.getSession().removeAttribute("currentUser");
		System.out.println("logging out");

		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String inlog() {
		return "login";
	}

	public User getCurrentUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("currentUser");

	}

}
