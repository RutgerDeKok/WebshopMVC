package rsvier.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
	
	private List<Product> products;
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getAllProducts(){
		products =  new ArrayList<>();
		productRepository.findAll().forEach(products::add);
		return products;
	}
	
	public Product getProduct(Long id){
		return productRepository.findOne(id);
	}
	
	public void addProduct(Product product){
		productRepository.save(product);
	}
	
	public void updateProducter(Product product){
		productRepository.save(product);
	}
	
	public void deleteproduct(Long id){
		productRepository.delete(id);
	}
	

}
