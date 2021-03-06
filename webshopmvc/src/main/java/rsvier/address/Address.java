package rsvier.address;

import javax.persistence.*;
import java.lang.reflect.Field;

@Entity
@Table(name = "Addresses")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 50, nullable = false)
	private String firstName;
	@Column(length = 50)
	private String insertion; 
	@Column(length = 10, nullable = false)
	private String familyName;
	@Column(length = 50, nullable = false)
	private String street;
	@Column(length = 5)
	private Integer number;
	@Column(length = 10)
	private String numAddition;
	@Column(length = 10)
	private String zipCode;
	@Column(length = 50, nullable = false)
	private String city;
        
	public Address(){
	}

    
  
    public Address(String firstName, String insertion, String familyName, String street, Integer number, String numAddition, String zipCode, String city) {
       
        this.firstName = firstName;
        this.insertion = insertion;
        this.familyName = familyName;
        this.street = street;
        this.number = number;
        this.numAddition = numAddition;
        this.zipCode = zipCode;
        this.city = city;
    }
	
//         public User getUser() {
//          return user;
//        }

//       public void setUser(User user) {
//           this.user = user;
//       }
       
        public long getId() {
		return id;
	}
        
        /* @Jurjen
        Overbodig, doet Hibernate al
        */
        public void setId(long id) {
		this.id = id;
	}

        public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getInsertion() {
		return insertion;
	}

	public void setInsertion(String insertion) {
		this.insertion = insertion;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getNumAddition() {
		return numAddition;
	}

	public void setNumAddition(String numAddition) {
		this.numAddition = numAddition;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
        
	@Override
    public String toString() {
        return firstName + " " + insertion + " " + familyName + "\n"
             + street + " " + number + " " + numAddition + "\n"
             + zipCode + " " + city;
    }

    public boolean isEmpty() {
		for (Field f : getClass().getDeclaredFields())
			try {
				if (f.get(this) != null) {
					return false;
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		return true;
	}

}

