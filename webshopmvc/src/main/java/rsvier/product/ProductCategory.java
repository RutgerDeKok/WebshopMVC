package rsvier.product;

public enum ProductCategory {

	MEDIUM_HARD("Medium-Hard"),
	SOFT_MOLD("Zacht-Schimmel"), 
	BLUE("Blauw"), 
	CREAM("Roomkaas"), 
	GOAT("Geiten"),
	ALL("Alles");
	


	private final String naamNL;

	private ProductCategory(String naamNL) {
		this.naamNL = naamNL;
	}
	
	public String getNL() {
		return naamNL;
	}
	
}

/* examples
 * 
 * MEDIUM_HARD: Edam, Gouda, Emmentaler, Cheddar
 * SOFT_MOLD:	Camenbert, Brie, Neufch�tel
 * BLUE:  		Roquefort, Gorgonzola and Stilton.
 * CREAM: 		Philadelphia, Boursin,  mascarpone
 * GOAT: 		Meahhhh
 * ALL:  		only used as a filter option in menu
 */










