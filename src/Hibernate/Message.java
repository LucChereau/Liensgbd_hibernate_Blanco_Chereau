package Hibernate;

import java.util.Date;
import java.util.List;

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
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Lien> Liste_Lien;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Mot_Cle> Liste_Mot_Cle;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Image> Liste_Image; 
	
	public List<Lien> getListe_Lien() {
		return Liste_Lien;
	}

	public void setListe_Lien(List<Lien> liste_Lien) {
		Liste_Lien = liste_Lien;
	}

	public List<Mot_Cle> getListe_Mot_Cle() {
		return Liste_Mot_Cle;
	}

	public void setListe_Mot_Cle(List<Mot_Cle> liste_Mot_Cle) {
		Liste_Mot_Cle = liste_Mot_Cle;
	}

	public List<Image> getListe_Image() {
		return Liste_Image;
	}

	public void setListe_Image(List<Image> liste_Image) {
		Liste_Image = liste_Image;
	}

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
