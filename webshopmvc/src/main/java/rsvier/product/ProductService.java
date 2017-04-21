package rsvier.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

@Service
public class ProductService {
    
    @Autowired
    ProductRepository dao;
    
     public List<Product> getAllProducts(){
     List<Product> list = new ArrayList<>();
     dao.findAll().forEach(list::add);
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