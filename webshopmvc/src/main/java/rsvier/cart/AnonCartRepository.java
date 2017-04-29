package rsvier.cart;

import org.springframework.data.repository.CrudRepository;

public interface AnonCartRepository extends CrudRepository<AnonymousCart, Long> {

    AnonymousCart getAnonymousCartBySessionId(String sessionId);

}
