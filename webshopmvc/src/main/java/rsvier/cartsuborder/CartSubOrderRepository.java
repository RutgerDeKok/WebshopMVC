package rsvier.cartsuborder;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartSubOrderRepository extends CrudRepository<CartSubOrder, Long>{

}
