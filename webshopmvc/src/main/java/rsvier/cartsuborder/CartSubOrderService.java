package rsvier.cartsuborder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rsvier.product.Product;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartSubOrderService {
    
    @Autowired
    CartSubOrderRepository dao;
    
    public List<CartSubOrder> getAllCartSubOrders(){
        List<CartSubOrder> list = new ArrayList<>();
        dao.findAll().forEach(list::add);
        return list;
    }
    
    public CartSubOrder getCartSubOrder(Long id){
        return dao.findOne(id);
    }
    
    public void updateCartSubOrder(Long id, CartSubOrder cartSubOrder){
       dao.save(cartSubOrder);
    }
    
    public void deleteCartSubOrder(CartSubOrder cartSubOrder){
         dao.delete(cartSubOrder);
    }

    public void createCartSubOrder(CartSubOrder cartSubOrder ){
         dao.save(cartSubOrder);
    }
    
    public List<CartSubOrder> getCartSubOrdersByProduct(Product product){
    	return dao.getCartSubOrdersByProduct(product);
    }
    
    
    
}