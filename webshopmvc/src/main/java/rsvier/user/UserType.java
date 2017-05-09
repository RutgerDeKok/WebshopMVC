package rsvier.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

public enum UserType {
	EMPLOYEE("Medewerker","Mw"),
	CUSTOMER("Klant", "Kl"),
	ADMIN("Admin", "Adm"),
	ALL("Alles","All");
	
	private final String naamNL;
	private final String kortNL;

	private UserType(String naamNL, String kortNL) {
		this.naamNL = naamNL;
		this.kortNL = kortNL;
	}
	
	public String getNL() {
		return naamNL;
	}
	
	public String getKort() {
		return kortNL;
	}

}
