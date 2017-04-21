package rsvier.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

@Service
public class CartService {
    
    @Autowired
    CartRepository dao;
    
     public List<Cart> getAllCarts(){
     List<Cart> list = new ArrayList<>();
     dao.findAll().forEach(list::add);
     return list;
  }
    
    public Cart getCart(Long id){
        return dao.findOne(id);
    
    }
    
    public void updateCart(Long id, Cart cart){
       dao.save(cart);
        
    }
    
     public void deleteCart(Cart cart){
         dao.delete(cart);
    }
     
      public void createCart(Cart cart){
         dao.save(cart);
    }
}