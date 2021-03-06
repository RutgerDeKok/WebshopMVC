package rsvier.cartsuborder;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import rsvier.product.Product;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "cart_suborders")
public class CartSubOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne // (cascade=CascadeType.PERSIST)
	@Fetch(FetchMode.JOIN)
	private Product product;
	@Column(length = 10, nullable = false)
	private int quantity;
	@Column(length = 10, nullable = false)
	private BigDecimal subTotal;

	public CartSubOrder() {
	}

	public CartSubOrder(long id, Product product, int quantity) {
		this.id = id; this.product = product; this.quantity = quantity;
	}
	
	public CartSubOrder(Product product, int quantity) {
		this.product = product; this.quantity = quantity;
		calculateSubTotal();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal price, int quantity) {
		subTotal = price.multiply(new BigDecimal(quantity));
		subTotal.setScale(2);
		
	}

	public void calculateSubTotal() {
		subTotal = product.getPrice().multiply(new BigDecimal(quantity));
	}

	

	@Override
	public String toString() {
		// Product - Aantal - Prijs - Subtotaal",
		int q = this.getQuantity();
		BigDecimal p = this.getProduct().getPrice();
		setSubTotal(p, q);
		p.setScale(2);
		String result = this.getProduct().getName() + "\t" + q + "\t" + p + "\t" + subTotal;

		return result;
	}


}
