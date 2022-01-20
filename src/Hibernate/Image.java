package Hibernate;

import javax.persistence.*;

import org.hibernate.type.BlobType;

import com.mysql.cj.jdbc.Blob;
@Entity 
public class Image {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id_Image; 
	@Lob
	private Blob Contenu_Image; 
	
	private String Lien_serveur; 
	@ManyToOne(cascade = CascadeType.ALL)
	private Message m;
	
	public Image() {
		Contenu_Image=null; 
		Lien_serveur=null; 
	}
	
	public Image(Blob Contenu_Image, String Lien_serveur, Message m) {
		this.Contenu_Image=Contenu_Image; 
		this.Lien_serveur=Lien_serveur; 
		this.m=m; 
	}
	
	public int getId_Image() {
		return id_Image;
	}
	public void setId_Image(int id_Image) {
		this.id_Image = id_Image;
	}
	public Blob getContenu_Image() {
		return Contenu_Image;
	}
	public void setContenu_Image(Blob contenu_Image) {
		Contenu_Image = contenu_Image;
	}
	public String getLien_serveur() {
		return Lien_serveur;
	}
	public void setLien_serveur(String lien_serveur) {
		Lien_serveur = lien_serveur;
	}
	public Message getM() {
		return m;
	}
	public void setM(Message m) {
		this.m = m;
	} 
}
