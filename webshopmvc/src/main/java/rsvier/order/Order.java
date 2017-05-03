package rsvier.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import rsvier.address.Address;
import rsvier.finalsuborder.FinalSubOrder;
import rsvier.user.User;




@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private User user;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	private List<FinalSubOrder> subOrders = new ArrayList<>();
	private LocalDate orderDate;
	@Column(length = 10, nullable = false)
	private BigDecimal totalPrice;

	private String firstName;
	@Column(length = 50)
	private String insertion;
	@Column(length = 10, nullable = false)
	private String familyName;
	@Column(length = 50, nullable = false)
	private String street;
	@Column(length = 5)
	private int number;
	@Column(length = 10)
	private String numAddition;
	@Column(length = 10)
	private String zipCode;
	@Column(length = 50, nullable = false)
	private String city;

	public Order() {
	}

	public void setDeliveryAdress(Address adress) {

		firstName = adress.getFirstName();
		insertion = adress.getInsertion();
		familyName = adress.getFamilyName();
		street = adress.getStreet();
		number = adress.getNumber();
		numAddition = adress.getNumAddition();
		zipCode = adress.getZipCode();
		city = adress.getCity();

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<FinalSubOrder> getSubOrders() {
		return subOrders;
	}


	public void addSubOrder(FinalSubOrder subOrder) {
		subOrders.add(subOrder);
	}

	public LocalDate getSaledate() {
		return orderDate;
	}

	public void setSaledate(LocalDate saledate) {
		this.orderDate = saledate;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	
	
	public LocalDate getOrderDate() {
		return orderDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getInsertion() {
		return insertion;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getStreet() {
		return street;
	}

	public int getNumber() {
		return number;
	}

	public String getNumAddition() {
		return numAddition;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		if (this.totalPrice == null) {
			this.totalPrice = new BigDecimal(0);
		}
		this.totalPrice = this.totalPrice.add(totalPrice);
	}

	public void calculateTotalPrice() {
		for (FinalSubOrder fso : subOrders) {
			BigDecimal subTotal = fso.getSubTotal();
			setTotalPrice(subTotal);
		}
	}

}
