package rsvier.user;

import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.Type;
import rsvier.address.Address;



@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String email;
	private String passHash;
	@Column(name = "type")
	@Enumerated(EnumType.STRING) // komt in de tabel als string, alternatief is
									// ORDINAL, komt als index (int)
	private UserType userType; // ENUM
	@OneToOne(cascade = CascadeType.ALL)
	private Address billingAddress;
	@Type(type = "true_false")
	private boolean enabled;
	/*@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private
	Role role;*/

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassHash() {
		return passHash;
	}

	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		userType = userType;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAdress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
