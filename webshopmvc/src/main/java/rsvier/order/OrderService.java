package rsvier.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

@Service
public class OrderService {
    
    @Autowired
    OrderRepository dao;
    
     public List<Order> getAllOrders(){
     List<Order> list = new ArrayList<>();
     dao.findAll().forEach(list::add);
     return list;
  }
    
    public Order getOrder(Long id){
        return dao.findOne(id);
    
    }
    
    public void updateOrder(Long id, Order order){
       dao.save(order);
        
    }
    
     public void deleteOrder(Order order){
         dao.delete(order);
    }
     
      public void createOrder (Order order ){
         dao.save(order);
    }
}