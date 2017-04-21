package rsvier.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {

	private final ProductService productService;
	private ProductCategory categoryFilter = ProductCategory.ALL;
	private String brandFilter = "";
	private List<ProductCategory> filteredCategories = new ArrayList<>();
	

	@Autowired
	public ProductController(ProductService repo) {
		this.productService = repo;
	}

	@RequestMapping(value = { "/products" })
	public String showProductList(Map<String, Object> model) {
		

		List<Product> products = new ArrayList<>();
		products.addAll(productService.getAllProducts());
		
		List<ProductCategory> categories = new ArrayList<>();
		for(int i=0;i<ProductCategory.values().length;i++){
			categories.add(ProductCategory.values()[i]);
		}

		System.out.println(categories);
		
		model.put("lijst", products);
		model.put("cat", categoryFilter);
		model.put("brand", brandFilter);
		model.put("categories", categories);
		return "products";
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
