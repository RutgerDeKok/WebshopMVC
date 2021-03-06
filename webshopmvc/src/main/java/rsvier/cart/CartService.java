package rsvier.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rsvier.cartsuborder.CartSubOrder;
import rsvier.cartsuborder.CartSubOrderService;
import rsvier.user.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    
    @Autowired
    CartRepository dao;
    @Autowired
    CartSubOrderService cartSubOrderService;
    
    public List<Cart> getAllCarts(){
         List<Cart> list = new ArrayList<>();
         dao.findAll().forEach(list::add);
         return list;
    }
    
    public Cart getCart(Long id){
        return dao.findOne(id);
    }
    
    public Cart getCartByUser(User user){
        return dao.findCartByUser(user);
    }
    
    public void updateCart(Cart cart){
       dao.save(cart);
    }
    
    public void deleteCart(Cart cart){
         dao.delete(cart);
    }

    public void createCart(Cart cart){
         dao.save(cart);
    }

    public void removeFromCart(Cart cart, CartSubOrder cso) {
        List<CartSubOrder> updatedList = cart.getSubOrders();
        updatedList.remove(cso);
        cart.setSubOrders(updatedList);
        updateCart(cart);
        cartSubOrderService.deleteCartSubOrder(cso);
    }

}