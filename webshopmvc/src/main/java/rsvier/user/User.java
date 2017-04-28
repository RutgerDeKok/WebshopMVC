package rsvier.user;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	private UserType UserType; // Enum
	@OneToMany(cascade = CascadeType.ALL)
	private List<Address> billingAddresses;

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

	/*
	 * @Jurjen get en set PassHash: vraag me af of dit nodig is wordt toch
	 * afgehandeld door PassHasher.java?
	 */
	public String getPassHash() {
		return passHash;
	}

	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}

	public UserType getUserType() {
		return UserType;
	}

	public void setUserType(UserType userType) {
		UserType = userType;
	}

	public List<Address> getBillingAddresses() {
		return billingAddresses;
	}

	public void setBillingAdresses(List<Address> billingAddresses) {
		this.billingAddresses = billingAddresses;
	}
        
        public void addBillingAdresses(Address billingAddress) {
                this.billingAddresses.add(billingAddress);
	}
}
