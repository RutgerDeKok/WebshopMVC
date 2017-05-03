/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsvier;

import java.math.BigDecimal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rsvier.address.Address;
import rsvier.cart.Cart;
import rsvier.cartsuborder.CartSubOrder;
import rsvier.finalsuborder.FinalSubOrder;
import rsvier.order.Order;
import rsvier.order.OrderController;
import rsvier.product.Product;
import rsvier.product.ProductCategory;
import rsvier.user.User;
import rsvier.user.UserType;

/**
 *
 * @author Jellie
 */
public class OrderControllerTest {
    
    OrderController orderController;
    Order order;
    Cart cart;
    CartSubOrder cartSubOrder1, cartSubOrder2;
    FinalSubOrder finalSubOrder;
    User user;
    Address address;
    Product product1, product2;
    
    public OrderControllerTest() {
    }
  
    @Before
    public void setUp() {
    orderController = new OrderController();
        
    product1 = new Product(1, "Hoi", "HOI", ProductCategory.BLUE, "Hoi", new BigDecimal("7"), 7);
    product2 = new Product(2, "Hoi2", "HOI2", ProductCategory.ALL, "Hoi2", new BigDecimal("7"), 7);
    address = new Address(1, "Hoi", "HOI", "Hoi", "HOI", 1, "Hoi", "HOI", "Hoi");
    user = new User(1, "hoi", "lwfeifkwefiwojefwef", UserType.ALL, address);
    cart = new Cart(1, user, address, new BigDecimal("7"));
    cartSubOrder1 = new CartSubOrder(1, product1, 2);
    cartSubOrder2 = new CartSubOrder(2, product2, 3);
    
    finalSubOrder = new FinalSubOrder();
    
    order = new Order();
    
    
    }
    
    @After
    public void tearDown() {
    }

   
   @Test
    public void hello() {
    
    
    }
}
