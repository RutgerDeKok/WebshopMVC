package rsvier.order;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rsvier.user.User;

public interface OrderRepository extends CrudRepository<Order, Long>{
	
	public List<Order> getOrdersByUser(User user);


}
