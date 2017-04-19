package rsvier.user;

public enum UserType {
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
