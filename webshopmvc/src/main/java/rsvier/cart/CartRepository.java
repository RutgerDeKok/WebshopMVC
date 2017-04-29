package rsvier.cart;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface CartRepository extends CrudRepository<Cart, Long> {

    Cart getCartByUserId(long userId);

}
