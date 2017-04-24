package rsvier.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{
	
	public User findUserByEmail(String email);


}
