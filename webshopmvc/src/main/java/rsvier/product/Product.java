package rsvier.product;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(length = 50, nullable = false)
	private String name;
	@Column(length = 50)
	private String brand;
	@Column(name = "category",length = 50,nullable = false)
	@Enumerated(EnumType.STRING) // komt in de tabel als string, alternatief is ORDINAL, komt als index (int)
	private ProductCategory category;  //Enum
	private String info; /* @Jurjen wat voor info? */
	@Column(length = 10,nullable = false)
	private BigDecimal price;
	@Column(length = 10,nullable = false)
	private int stockCount;
        
	
	public Product(){
	}
	
	public long getId() {
		return id;
	}

        /* @Jurjen
        Overbodig, doet Hibernate al
        */
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getStockCount() {
		return stockCount;
	}

	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}

}
