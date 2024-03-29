package Hibernate;

import java.util.ArrayList;
import java.util.List;

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
	
	@OneToMany(mappedBy="u", cascade = CascadeType.ALL)
	private List<Message> Liste_Message;
	public Utilisateur() {
		nom=null; 
		prenom=null; 
		mail=null; 
		mot_de_passe=null; 
		a=null; 
		Liste_Message=new ArrayList<Message>(); 
	}

	public Utilisateur(String nom, String prenom, String mail, String mot_de_passe,Adresse a) {
		this.nom=nom; 
		this.prenom=prenom; 
		this.mail=mail; 
		this.mot_de_passe=mot_de_passe; 
		this.a=a; 
		this.Liste_Message=new ArrayList<Message>();
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

	public List<Message> getListe_Message() {
		return Liste_Message;
	}
	public void setListe_Message(List<Message> liste_Message) {
		Liste_Message = liste_Message;
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
