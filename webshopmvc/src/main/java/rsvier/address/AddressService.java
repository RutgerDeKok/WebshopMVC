package rsvier.address;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

@Service
public class AddressService {
    
    @Autowired
    AddressRepository dao;
    
     public List<Address> getAllAddresses(){
     List<Address> list = new ArrayList<>();
     dao.findAll().forEach(list::add);
     return list;
  }
    
    public Address getAddress(Long id){
        return dao.findOne(id);
    
    }
    
    public void updateCartSubOrder(Long id, Address address){
       dao.save(address);
        
    }
    
     public void deleteAddress(Address address){
         dao.delete(address);
    }
     
      public void createAddress(Address address ){
         dao.save(address);
    }
}