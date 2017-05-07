package rsvier.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

public enum UserType {
	ADMIN("Admin"),
	EMPLOYEE("Medewerker"),
	CUSTOMER("Klant"),
	ALL("Alles");

	private final String naamNL;

	private UserType(String naamNL) {
		this.naamNL = naamNL;
	}
	
	public String getNL() {
		return naamNL;
	}

}
