package rsvier;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import rsvier.product.Product;
import rsvier.product.ProductService;
import rsvier.user.User;


@Controller
class WelcomeController {


    @Autowired
    ProductService productService;
    
	@RequestMapping(value ="/")
	public String welcome(Model model, HttpServletRequest request) {
        List<Product> list = productService.getAllProducts();
        User test = (User)request.getSession().getAttribute("currentUser");
        if(test==null){
        	System.out.println("no test user");
        }else{
        System.out.println("naam van test user is: "+test.getEmail());
        }
    
        
		model.addAttribute("productlist", list);
		return "welcome";
	}
		
//	protected void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//                .antMatchers("/", "/home").permitAll()
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//            .logout()
//                .permitAll();
//	}
	
	
	
	
//	@RequestMapping(value = { "/welcome" } , method = RequestMethod.POST)
//	public String redirectWelcome() {
//		System.out.println("in de redirect welcom methode ");
//		return "redirect:/";
//	}
	
		
        
        @RequestMapping("/boerPiet")
	public String contact() {
		return "BoerPiet";
	}
        
        @RequestMapping("/Kaas")
	public String Keuze() {
		return "KaasKeuzeMenu";
	}
        
         @RequestMapping("/wijzigAdres")
	public String AddressThing() {
		return "wijzigAdres";
	}

          /*  @RequestMapping("/error")
	public String Error() {
		return "error";
	}*/

}

