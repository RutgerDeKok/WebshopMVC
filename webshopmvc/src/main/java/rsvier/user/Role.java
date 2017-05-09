package rsvier.user;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * Created by J on 5/3/2017.
 */

@Entity
@Table(name = "roles")
public class Role {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   private String role;
   @OneToMany(cascade = CascadeType.ALL)
   @JoinTable(name="users_roles",
           joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
           inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
   )
   private Set<User> userRoles;

   public Role() {

   }

   public Role(String role) {
      this.role = role;
   }

   @Override
   public String toString() {
      return "ROLE_" + role;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getRole() {
      return role;
   }

   public void setRole(String role) {
      this.role = role;
   }

   public Set<User> getUserRoles() {
      return userRoles;
   }

   public void setUserRoles(Set<User> userRoles) {
      this.userRoles = userRoles;
   }
}
