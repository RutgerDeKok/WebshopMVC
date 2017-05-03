package rsvier.user;

import rsvier.address.Address;

import javax.persistence.*;



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
	@OneToOne(cascade = CascadeType.ALL)
	private Address billingAddress;

	public User() {
	}

    public User(long id, String email, String passHash, UserType UserType, Address billingAddress) {
        this.id = id;
        this.email = email;
        this.passHash = passHash;
        this.UserType = UserType;
        this.billingAddress = billingAddress;
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
		return UserType;
	}

	public void setUserType(UserType userType) {
		UserType = userType;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAdress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}
        
}
