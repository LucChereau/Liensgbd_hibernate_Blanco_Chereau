package Hibernate;

import java.util.ArrayList;
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
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Utilisateur u;
	
	@OneToMany(mappedBy="m", cascade = CascadeType.ALL)
	private List<Lien> Liste_Lien;
	
	@OneToMany(mappedBy="m",cascade = CascadeType.ALL)
	private List<Mot_Cle> Liste_Mot_Cle;
	
	@OneToMany(mappedBy="m",cascade = CascadeType.ALL)
	private List<Image> Liste_Image; 
	
	public Message() {
		titre=null; 
		texte=null; 
		date_de_post=null; 
		Liste_Lien=new ArrayList<Lien>(); 
		Liste_Mot_Cle=new ArrayList<Mot_Cle>(); 
		Liste_Image=new ArrayList<Image>(); 
	}
	
	public List<Lien> getListe_Lien() {
		return Liste_Lien;
	}
	public Message(String titre, String texte, Date date_de_poste,Utilisateur u) {
		this.titre=titre; 
		this.texte=texte; 
		this.date_de_post=date_de_poste;
		this.u=u; 
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
