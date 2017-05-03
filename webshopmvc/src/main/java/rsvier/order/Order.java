package rsvier.order;

import rsvier.address.Address;
import rsvier.finalsuborder.FinalSubOrder;
import rsvier.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




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
	private Date orderDate;
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
	
	public void setSubOrder(List<FinalSubOrder> subOrders) {
		this.subOrders=subOrders;
	}

	public Date getSaledate() {
		return orderDate;
	}

	public void setSaledate(Date saledate) {
		this.orderDate = saledate;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	
	
	public Date getOrderDate() {
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
			this.totalPrice = new BigDecimal("0");
		}
		this.totalPrice = this.totalPrice.add(totalPrice);
	}

	public void calculateTotalPrice() {
		for (FinalSubOrder fso : subOrders) {
			BigDecimal subTotal = fso.getSubTotal();
			setTotalPrice(subTotal);
		}
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderDate=" + orderDate + ", totalPrice=" + totalPrice + ", firstName="
				+ firstName + ", insertion=" + insertion + ", familyName=" + familyName + ", street=" + street
				+ ", number=" + number + ", numAddition=" + numAddition + ", zipCode=" + zipCode + ", city=" + city
				+ "]";
	}
	

}
