package rsvier.cartsuborder;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rsvier.product.Product;

public interface CartSubOrderRepository extends CrudRepository<CartSubOrder, Long>{
	
	
	List<CartSubOrder> getCartSubOrdersByProduct(Product product);

}

