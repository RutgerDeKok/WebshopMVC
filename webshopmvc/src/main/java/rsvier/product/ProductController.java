package rsvier.product;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ProductController {

	private final ProductService productService;
	private ProductCategory categoryFilter;
	// private String brandFilter = "";

	@Autowired
	public ProductController(ProductService repo) {
		this.productService = repo;
	}
	
	@RequestMapping(value =  { "/products" })
	public String showProductList(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		
		categoryFilter= (ProductCategory) request.getSession().getAttribute("categoryFilter");
		System.out.println("categoryFilter from session is null?:"+categoryFilter==null);
		
		if(categoryFilter ==null){
			categoryFilter = ProductCategory.ALL;
		}

		List<Product> products = productService.getProductsByCat(categoryFilter);

//		if (products.isEmpty()) {
//			products.addAll(productService.getAllProducts());
//		}

		System.out.println("De filter in controller is:" + categoryFilter.getNL());
		model.put("formobject", new FormObject());
		model.put("lijst", products);
		model.put("categories", ProductCategory.values());
		return "products";
	}
	
	

	
	@RequestMapping(value = "products/filter", method = RequestMethod.POST)
	public @ResponseBody void filterProducts(@RequestParam("filter") String radioWaarde,  HttpServletRequest request, HttpServletResponse response) {		
//		System.out.println(request.getAttribute("filter"));
//		ProductCategory categoryFilter = ProductCategory.valueOf((String) request.getAttribute("filter"));
//		categoryFilter = formobject.getFilter2();
		System.out.println(radioWaarde);
		ProductCategory categoryFilter = ProductCategory.valueOf(radioWaarde);
		System.out.println("De filter in filterProducts methos is:" + categoryFilter.getNL());
		
		request.getSession().setAttribute("categoryFilter", categoryFilter);

		try {
			response.sendRedirect("/products");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	

	public ProductCategory getCategoryFilter() {
		return categoryFilter;
	}

	public void setCategoryFilter(ProductCategory categoryFilter) {
		this.categoryFilter = categoryFilter;
	}

//	public String getBrandFilter() {
//		return brandFilter;
//	}
//
//	public void setBrandFilter(String brandFilter) {
//		this.brandFilter = brandFilter;
//	}



}
