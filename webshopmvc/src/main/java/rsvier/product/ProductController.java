package rsvier.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService repo) {
		this.productService = repo;
	}

	@RequestMapping(value = { "/products.html" })
	public String showProductList(Map<String, Object> model) {
		
//		ProductList products = new ProductList();
//		products.getProductList().addAll(this.productService.getAllProducts());
		List<Product> products = new ArrayList<>();
		products .addAll(this.productService.getAllProducts());
		
		model.put("lijst", products);
		return "products";
	}

}
