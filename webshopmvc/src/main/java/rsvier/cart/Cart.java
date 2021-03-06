package rsvier.cart;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import rsvier.address.Address;
import rsvier.cartsuborder.CartSubOrder;
import rsvier.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class Cart implements Serializable {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne
	private User user;
	@OneToOne(cascade = CascadeType.ALL)
	private Address deliveryAddress;
	@OneToMany(cascade=CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "cart_id")
	private List<CartSubOrder> subOrders = new ArrayList<>();
	@Column(length = 10)
	private BigDecimal totalPrice;


	public Cart() {
	}

    public Cart(long id, User user, Address deliveryAddress, BigDecimal totalPrice) {
        this.id = id;
        this.user = user;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
    }
 
//	public Cart(long id, User user) {
//		this.id = id; this.user = user;
//	}

	public String toString() {
		return "Aantal producten: " + subOrders.size() + ", totaal: " + totalPrice;
	}

	public User getUser() {
		return user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public void addSubOrder(CartSubOrder subOrder) {
		subOrders.add(subOrder);
	}

	public void setSubOrders(List<CartSubOrder> subOrders) {
		this.subOrders = subOrders;
	}

	public List<CartSubOrder> getSubOrders() {
		return subOrders;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
        if (this.totalPrice == null) {
            this.totalPrice = new BigDecimal(0);
        }
		this.totalPrice = this.totalPrice.add(totalPrice);
	}
        
    public BigDecimal calculateTotalPrice() {
    	totalPrice = BigDecimal.ZERO;
         for (CartSubOrder sub : subOrders) {
             totalPrice= totalPrice.add(sub.getSubTotal()); 
         }
         return totalPrice;
	}

	protected void emptyCart() {
		subOrders.clear();
	}



}
