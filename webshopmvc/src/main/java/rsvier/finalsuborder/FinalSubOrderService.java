package rsvier.finalsuborder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

@Service
public class FinalSubOrderService {
    
    @Autowired
    FinalSubOrderRepository dao;
    
     public List<FinalSubOrder> getAllFinalSubOrders(){
     List<FinalSubOrder> list = new ArrayList<>();
     dao.findAll().forEach(list::add);
     return list;
  }
    
    public FinalSubOrder getFinalSubOrder(Long id){
        return dao.findOne(id);
    
    }
    
    public void updateFinalSubOrder(Long id, FinalSubOrder finalSubOrder){
       dao.save(finalSubOrder);
        
    }
    
     public void deleteFinalSubOrder(FinalSubOrder finalSubOrder){
         dao.delete(finalSubOrder);
    }
     
      public void createFinalSubOrder(FinalSubOrder finalSubOrder ){
         dao.save(finalSubOrder);
    }
}