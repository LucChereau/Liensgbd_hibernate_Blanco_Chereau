package Hibernate;
import javax.persistence.*; 
@Entity
public class Lien {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id_lien;
	private String adresse_Lien; 
	private String texte; 
	@ManyToOne(cascade = CascadeType.ALL)
	private Message m;
	public int getId_lien() {
		return id_lien;
	}
	public void setId_lien(int id_lien) {
		this.id_lien = id_lien;
	}
	public String getAdresse_Lien() {
		return adresse_Lien;
	}
	public void setAdresse_Lien(String adresse_Lien) {
		this.adresse_Lien = adresse_Lien;
	}
	public String getTexte() {
		return texte;
	}
	public void setTexte(String texte) {
		this.texte = texte;
	}
	public Message getM() {
		return m;
	}
	public void setM(Message m) {
		this.m = m;
	} 
}
