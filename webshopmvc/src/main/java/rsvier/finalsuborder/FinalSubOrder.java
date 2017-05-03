package rsvier.finalsuborder;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import rsvier.cartsuborder.CartSubOrder;
import rsvier.product.ProductCategory;



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
    
    
	public String getPrd_Name() {
        return prd_name;
    }
	
    public int getQuantity() {
        return quantity;
    }
    
    public BigDecimal getSubTotal() {
        return subTotal;
    }
}
