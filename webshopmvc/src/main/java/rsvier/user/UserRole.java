package rsvier.user;

import javax.persistence.*;

/**
 * Created by J on 4/28/2017.
 */
@Entity
@Table(name="user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_role_id")
    private long userRoleId;
    private long userId;
    private String userRole;

    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
