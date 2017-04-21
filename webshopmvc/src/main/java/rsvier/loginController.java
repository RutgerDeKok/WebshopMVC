/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsvier;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rsvier.product.Product;
import rsvier.product.ProductCategory;
import rsvier.user.User;

/**
 *
 * @author Frank
 */

@Controller
public class loginController {



	// @RequestMapping("/")
	// public String welcome() {
	// return "welcome";
	// }

	@RequestMapping("/inlog.html")
	public String welcomeInlog(Map<String, Object> model) {

		Product product1 = new Product();
		product1.setName("kaas");
		product1.setCategory(ProductCategory.GOAT);
		product1.setPrice(new BigDecimal("12.30"));

		
		List<Product> products = new ArrayList<>();
		products.add(product1);
		products.add(product1);
		products.add(product1);
		products.add(product1);

		model.put("lijst", products);
		

		
		return "inlog";
	}
        
        
        

}
    
    

