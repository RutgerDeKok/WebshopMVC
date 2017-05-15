package rsvier.product;

import javax.persistence.*;
import java.math.BigDecimal;

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

	public Product(long id, String name, String brand, ProductCategory category, String info, BigDecimal price, int stockCount) {
		this.id=id; this.name=name; this.brand=brand; this.category=category; this.info=info; this.price=price; this.stockCount=stockCount;
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
