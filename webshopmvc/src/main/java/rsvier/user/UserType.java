package rsvier.user;

public enum UserType {

	EMPLOYEE("Medewerker"),
	CUSTOMER("Klant"),
	ADMIN("Admin"),
	ALL("Alles");
	
	private final String naamNL;

	UserType(String naamNL) {
		this.naamNL = naamNL;
	}
	
	public String getNL() {
		return naamNL;
	}

}
