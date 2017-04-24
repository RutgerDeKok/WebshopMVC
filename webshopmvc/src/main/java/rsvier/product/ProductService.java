package rsvier.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

@Service
public class ProductService {
    
    
    Product product = new Product(1, "Groene kaas", "Groen", ProductCategory.CREAM, "informatie", (new BigDecimal("2.50")), 200);
    Product product1 = new Product(2, "Gele kaas", "geel", ProductCategory.GOAT, "info", (new BigDecimal("4.99")), 200);
    Product product2 = new Product(3, "Kaas de la kaas", "uberkaas", ProductCategory.MEDIUM_HARD, "info", (new BigDecimal("1.99")), 50);
    
    
    @Autowired
    ProductRepository dao;
    
     public List<Product> getAllProducts(){
     List<Product> list = new ArrayList<>();
     dao.findAll().forEach(list::add);
     list.add(product);
     list.add(product1);
     list.add(product2);
     return list;
  }
    
    public Product getProduct(Long id){
        return dao.findOne(id);
    
    }
    
    public void updateProduct(Long id, Product product){
       dao.save(product);
        
    }
    
     public void deleteProduct(Product product){
         dao.delete(product);
    }
     
      public void createProduct(Product product){
         dao.save(product);
    }
}