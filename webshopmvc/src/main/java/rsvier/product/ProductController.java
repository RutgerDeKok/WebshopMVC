package rsvier.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {

	private final ProductService productService;
	private ProductCategory categoryFilter;
	private String brandFilter = "";
	private List<ProductCategory> filteredCategories = new ArrayList<>();
	

	@Autowired
	public ProductController(ProductService repo) {
		this.productService = repo;
	}

//	@RequestMapping(value = { "/products" },method = RequestMethod.GET)
//	public String showProductList(@RequestParam("categoryFilter") ProductCategory categoryFilter, Map<String, Object> model) {

	@RequestMapping(value = { "/products" })
	public String showProductList( Map<String, Object> model,  HttpServletRequest request, 
	        HttpServletResponse response) {
		

		List<Product> products = new ArrayList<>();
		products.addAll(productService.getAllProducts());
		
		List<ProductCategory> categories = new ArrayList<>();
		for(int i=0;i<ProductCategory.values().length;i++){
			categories.add(ProductCategory.values()[i]);
		}
		
		categoryFilter= (ProductCategory) request.getSession().getAttribute("categoryFilter");
		System.out.println("categoryFilter from session is null?:"+categoryFilter==null);
		if(categoryFilter ==null){
			System.out.println("cat is null");
			categoryFilter = ProductCategory.ALL;
			
		}
			

		System.out.println(categories);
		
		System.out.println("De filter in controller is:"+categoryFilter.getNL());
		
		model.put("lijst", products);
		model.put("cat", categoryFilter);
		model.put("brand", brandFilter);
		model.put("categories", categories);
		return "products";
	}
	
	
	
//	@RequestMapping(value = "products/{objectId}", method = RequestMethod.GET)
//	public @ResponseBody void filterProducts(@PathVariable("objectId") String filterString, HttpServletRequest request, 
//	        HttpServletResponse response) {
	@RequestMapping(value = "products/f", method = RequestMethod.POST)
	public @ResponseBody void filterProducts( HttpServletRequest request, 
	        HttpServletResponse response) {
		String filterString =  request.getParameter("filter");
		
		
		System.out.println("filter : "+filterString);
		ProductCategory categoryFilter = ProductCategory.valueOf(filterString);
		System.out.println("De filter in filterProducts methos is:"+categoryFilter.getNL());
		request.getSession().setAttribute("categoryFilter", categoryFilter);
		
		
		try {
			response.sendRedirect("/products");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	

	public ProductCategory getCategoryFilter() {
		return categoryFilter;
	}

	public void setCategoryFilter(ProductCategory categoryFilter) {
		this.categoryFilter = categoryFilter;
	}

	public String getBrandFilter() {
		return brandFilter;
	}

	public void setBrandFilter(String brandFilter) {
		this.brandFilter = brandFilter;
	}
	

	public List<ProductCategory> getFilteredCategories() {
		return filteredCategories;
	}

	public void setFilteredCategories(List<ProductCategory> filteredCategories) {
		this.filteredCategories = filteredCategories;
	}

	

}
