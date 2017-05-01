package rsvier.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rsvier.user.UserRole;

/**
 * Created by J on 4/28/2017.
 */

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    UserRole findByUserRole(String userRole);

}
