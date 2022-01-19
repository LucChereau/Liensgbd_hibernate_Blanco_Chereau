package Hibernate;

import javax.persistence.*;

@Entity
public class Utilisateur {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String nom;
	private String prenom;
	private String mail;
	private String mot_de_passe;
	
	@OneToOne(mappedBy="u", cascade=CascadeType.ALL)
	private Adresse a;

	public Utilisateur(String nom, String prenom, String mail, String mot_de_passe,Adresse a) {
		this.nom=nom; 
		this.prenom=prenom; 
		this.mail=mail; 
		this.mot_de_passe=mot_de_passe; 
		this.a=a; 
		
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMot_de_passe() {
		return mot_de_passe;
	}

	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}

	public Adresse getA() {
		return a;
	}

	public void setA(Adresse a) {
		this.a = a;
	}
}
