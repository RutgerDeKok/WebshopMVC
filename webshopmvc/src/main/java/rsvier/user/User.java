package rsvier.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import rsvier.address.Address;

import java.io.Serializable;


@Entity
@Table(name = "users")
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String email;
	private String passHash;
	@Column(name = "type")
	@Enumerated(EnumType.STRING) // komt in de tabel als string, alternatief is
									// ORDINAL, komt als index (int)
	private UserType UserType; // Enum
	@OneToOne(cascade = CascadeType.ALL)
	private Address billingAddress;
	@Type(type="true_false")
	private boolean enabled;

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

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAdress(Address billingAddress) {
		this.setBillingAddress(billingAddress);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
