package rsvier.finalsuborder;

import rsvier.cartsuborder.CartSubOrder;
import rsvier.product.ProductCategory;

import javax.persistence.*;
import java.math.BigDecimal;



@Entity
@Table(name = "final_suborders")
public class FinalSubOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 10, nullable = false)
    private int quantity;
    @Column(length = 50, nullable = false)
    private String prd_name;
    @Column(length = 50)
    private String prd_brand;
    @Column(name = "prd_category",length = 50,nullable = false)
    @Enumerated(EnumType.STRING) // komt in de tabel als string, alternatief is ORDINAL, komt als index (int)
    private ProductCategory prd_category;  //Enum
    private BigDecimal item_price;
    @Column(name = "sub_total",length = 10,nullable = false)
    private BigDecimal subTotal;	

    public FinalSubOrder() { 
    }
    
    public FinalSubOrder(CartSubOrder sub) {
            quantity = sub.getQuantity();
            prd_name = sub.getProduct().getName();
            prd_brand = sub.getProduct().getBrand();
            prd_category = sub.getProduct().getCategory();
            item_price = sub.getProduct().getPrice();
            subTotal = sub.getSubTotal();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrd_name() {
        return prd_name;
    }

    public void setPrd_name(String prd_name) {
        this.prd_name = prd_name;
    }

    public String getPrd_brand() {
        return prd_brand;
    }

    public void setPrd_brand(String prd_brand) {
        this.prd_brand = prd_brand;
    }

    public ProductCategory getPrd_category() {
        return prd_category;
    }

    public void setPrd_category(ProductCategory prd_category) {
        this.prd_category = prd_category;
    }

    public BigDecimal getItem_price() {
        return item_price;
    }

    public void setItem_price(BigDecimal item_price) {
        this.item_price = item_price;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

	@Override
	public String toString() {
		return "FinalSubOrder [quantity=" + quantity + ", prd_name=" + prd_name + ", prd_brand=" + prd_brand
				+ ", prd_category=" + prd_category + ", subTotal=" + subTotal + "]";
	}
	
}