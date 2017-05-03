package rsvier.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public String showProductList(Map<String, Object> model, HttpServletRequest request) {
		
		putProductsInSession(request.getSession());
		model.put("categories", ProductCategory.values());
		return "products";
	}
	
	@RequestMapping(value =  { "/employees/products" })
	public String showProductListEmployees(Map<String, Object> model, HttpServletRequest request) {
		
		putProductsInSession(request.getSession());
		model.put("categories", ProductCategory.values());
		return "emp_products";
	}
	
	

	
	@RequestMapping(value = "products/filter", method = RequestMethod.POST)
	public @ResponseBody void filterProducts(@RequestParam("filter") String radioWaarde,  HttpServletRequest request, HttpServletResponse response) {		

		ProductCategory categoryFilter = ProductCategory.valueOf(radioWaarde);
		request.getSession().setAttribute("categoryFilter", categoryFilter);

		try {
			response.sendRedirect("/products");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@RequestMapping(value = "employees/products/filter", method = RequestMethod.POST)
	public @ResponseBody void filterProductsEdit(@RequestParam("filter") String radioWaarde,  HttpServletRequest request, HttpServletResponse response) {		

		ProductCategory categoryFilter = ProductCategory.valueOf(radioWaarde);
		request.getSession().setAttribute("categoryFilter", categoryFilter);

		try {
			response.sendRedirect("/employees/products");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@RequestMapping(value = "employees/products/delete", method = RequestMethod.POST)
	public @ResponseBody void deleteProduct(@RequestParam("delindex") String choice, HttpServletRequest request, HttpServletResponse response) {		

		System.out.println("delete product methode");
		@SuppressWarnings("unchecked")
		List<Product> lijst = (ArrayList<Product>) request.getSession().getAttribute("lijst");
		int prodIndex = (Integer.parseInt(choice));
		System.out.println("prodIndex is: " + prodIndex);
		//TODO implement the method
		Product prod = (Product)lijst.get(prodIndex);
		System.out.println("product to be deleted id: "+prod.getId()+" name: "+prod.getName());
		try {  // cannot delete a product if it is used as a key inside a suborder
		productService.deleteproduct(prod.getId());
		lijst.remove(prodIndex);
		} catch (Exception e){
			System.out.println(e.getMessage());
//			putProductsInSession(request.getSession());
		}
		try {
			response.sendRedirect("/employees/products");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@RequestMapping(value = "employees/products/new", method = RequestMethod.GET)
	public @ResponseBody void newProduct(HttpSession session, HttpServletResponse response){
			
		Product product = new Product();
		//om null pointer in het pull down menu te voorkomen
		// enum waarde toevoegen
		product.setCategory(ProductCategory.values()[0]);
		
		session.setAttribute("product", product);
		
		try {
			response.sendRedirect("/product_edit");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	@RequestMapping(value = "/product_edit", method = RequestMethod.GET)
	public String gotoProductEdit(Map<String, Object> model) {
		
		model.put("categories", ProductCategory.values());
		return "product_edit";
	}
	
	@RequestMapping(value = "employees/products/update", method = RequestMethod.POST)
	public @ResponseBody void updateProduct(@RequestParam("productIndex") String productIndex, 
			HttpSession session, HttpServletResponse response){
		@SuppressWarnings("unchecked")
		List<Product> lijst = (ArrayList<Product>) session.getAttribute("lijst");
		int prodIndex = (Integer.parseInt(productIndex));
		System.out.println("prodIndex is: " + prodIndex);
		
		Product product = (Product)lijst.get(prodIndex);
		session.setAttribute("product", product);
		
		try {
			response.sendRedirect("/product_edit");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/updateProduct/ok", method = RequestMethod.POST)
	public void voegAdrestoe(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("Product gegevens worden verwerkt");
		Product updateProduct = (Product) request.getSession().getAttribute("product");
		System.out.println("cat is: "+request.getParameter("cat"));
		updateProduct.setName(request.getParameter("name"));
		updateProduct.setBrand(request.getParameter("brand"));
		updateProduct.setInfo(request.getParameter("info"));
		updateProduct.setStockCount(Integer.parseInt(request.getParameter("stock")));
		updateProduct.setPrice(new BigDecimal(request.getParameter("price")));
		updateProduct.setCategory(ProductCategory.valueOf(request.getParameter("cat")));

		
			System.out.println("Gegevens worden opgeslagen in DB");
			
			// als nieuw product, na opslaan nieuwe lijst uit DB halen
			System.out.println("id = "+updateProduct.getId());
			if(updateProduct.getId()==0){
				//dwingt het halen van een verse lijst uit de bd
				// bij bekijken van alle producten
				productService.clearLocalList();
			}
			productService.updateProduct(updateProduct);
		
		request.getSession().removeAttribute("product");
			
		try {
			response.sendRedirect("/employees/products");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	

	private void putProductsInSession(HttpSession session) {
		System.out.println("running put products in session");
		
		categoryFilter= (ProductCategory) session.getAttribute("categoryFilter");
		
		if(categoryFilter ==null){
			categoryFilter = ProductCategory.ALL;
		}

		List<Product> products = productService.getProductsByCat(categoryFilter);
		System.out.println("products lijst length is: "+products.size());
		session.setAttribute("lijst", products);
		session.setAttribute("catFilter", categoryFilter);
	}

}
