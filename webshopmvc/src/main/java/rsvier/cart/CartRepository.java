package rsvier.cart;

import org.springframework.data.repository.CrudRepository;
import rsvier.user.User;

public interface CartRepository extends CrudRepository<Cart, Long> {
	
	Cart findCartByUser(User user);

}
