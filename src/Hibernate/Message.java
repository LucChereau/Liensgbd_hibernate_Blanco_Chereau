package Hibernate;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String titre;
	private String texte;
	private Date date_de_post;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Utilisateur u;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public Date getDate_de_post() {
		return date_de_post;
	}

	public void setDate_de_post(Date date_de_post) {
		this.date_de_post = date_de_post;
	}

	public Utilisateur getU() {
		return u;
	}

	public void setU(Utilisateur u) {
		this.u = u;
	}
	
	
}
