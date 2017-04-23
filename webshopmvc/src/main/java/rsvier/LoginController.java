package rsvier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rsvier.product.Product;
import rsvier.product.ProductCategory;
import rsvier.user.User;
import rsvier.user.UserService;

/**
 *
 * @author Frank
 */

@Controller
public class LoginController {



	// @RequestMapping("/")
	// public String welcome() {
	// return "welcome";
	// }
    
    private final UserService UserService;

	@Autowired
	public LoginController(UserService iets) {
		this.UserService = iets;
	}
    
    

	@RequestMapping(value = { "/login.html" })
        public String inlog(){
        return "login";
        }
        

		
		
	}
        
        
        


    
    

