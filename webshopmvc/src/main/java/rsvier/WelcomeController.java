package rsvier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import rsvier.product.Product;
import rsvier.product.ProductCategory;

@Controller
class WelcomeController {

	// @RequestMapping("/")
	// public String welcome() {
	// return "welcome";
	// }

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {

		Product product1 = new Product();
		product1.setName("kaas");
		product1.setCategory(ProductCategory.GOAT);
		product1.setPrice(new BigDecimal("12.30"));

		List<String> list = Arrays.asList(new String[] { "een", "twee", "drie", "vier", "vijf" });
		List<Product> products = new ArrayList<>();
		products.add(product1);
		products.add(product1);
		products.add(product1);
		products.add(product1);

		model.put("lijst", products);
		return "welcome";
	}

}