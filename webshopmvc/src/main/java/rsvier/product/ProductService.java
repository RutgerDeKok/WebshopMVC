package rsvier.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	Product product = new Product(1, "Groene kaas", "Groen", ProductCategory.CREAM, "informatie",
			(new BigDecimal("2.50")), 200);
	Product product1 = new Product(2, "Gele kaas", "geel", ProductCategory.GOAT, "info", (new BigDecimal("4.99")), 200);
	Product product2 = new Product(3, "Kaas de la kaas", "uberkaas", ProductCategory.MEDIUM_HARD, "info",
			(new BigDecimal("1.99")), 50);

	private List<Product> products;
	private List<Product> filteredProducts;
	
	@Autowired
	private ProductRepository productRepository;

	public List<Product> getAllProducts() {
		products = new ArrayList<>();
		productRepository.findAll().forEach(products::add);
		filteredProducts=getfilterProductenByCat(ProductCategory.ALL);
		return filteredProducts;
	}

	public List<Product> getfilterProductenByCat(ProductCategory filterCategory) {
		if(filteredProducts==null){
			System.out.println("creating new array list");
			filteredProducts = new ArrayList<>(products);
		}
		filteredProducts.clear();
		for (Product prod : products) {
			if (filterCategory == ProductCategory.ALL || prod.getCategory() == filterCategory) {
				filteredProducts.add(prod);
			}
		}
		return filteredProducts;

	}

	public Product getProduct(Long id) {
		return productRepository.findOne(id);
	}

	public void addProduct(Product product) {
		productRepository.save(product);
	}

	public void updateProducter(Product product) {
		productRepository.save(product);
	}

	public void deleteproduct(Long id) {
		productRepository.delete(id);
	}

	public List<Product> getAllProductsMock() {
		List<Product> list = new ArrayList<>();
		list.add(product);
		list.add(product1);
		list.add(product2);
		return list;
	}

}
