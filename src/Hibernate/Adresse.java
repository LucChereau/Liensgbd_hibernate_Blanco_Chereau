package Hibernate;

import javax.persistence.*;

@Entity
public class Adresse {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String City;
	private String Street;
	private String State;
	private String PostalCode;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Utilisateur u;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getStreet() {
		return Street;
	}

	public void setStreet(String street) {
		Street = street;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getPostalCode() {
		return PostalCode;
	}

	public void setPostalCode(String postalCode) {
		PostalCode = postalCode;
	}

	public Utilisateur getP() {
		return u;
	}

	public void setP(Utilisateur u) {
		this.u = u;
	}
}
