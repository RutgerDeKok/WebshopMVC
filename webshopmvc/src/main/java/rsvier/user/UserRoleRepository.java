package rsvier.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by J on 4/28/2017.
 */

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    @Query("select a.userRole from UserRole a, User b where b.email=?1 and a.userId=b.id")
    public List<String> findRoleByEmail(String email);

}
