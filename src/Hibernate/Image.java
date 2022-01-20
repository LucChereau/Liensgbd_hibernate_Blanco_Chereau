package Hibernate;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

import javax.persistence.*;
@Entity 
public class Image implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id_Image; 
	
	private byte[] Contenu_Image; 
	
	private String Lien_serveur; 
	@ManyToOne(cascade = CascadeType.ALL)
	private Message m;
	
	public Image() {
		Contenu_Image=null; 
		Lien_serveur=null; 
	}
	
	public Image(String Lien_serveur, Message m) { 
		this.Lien_serveur=Lien_serveur; 
		File file = new File(Lien_serveur);
		byte[] imageData = new byte[(int) file.length()];
		try {
		    FileInputStream fileInputStream = new FileInputStream(file);
		    fileInputStream.read(imageData);
		    fileInputStream.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		this.Contenu_Image=imageData; 
		this.m=m; 
	}
	
	public int getId_Image() {
		return id_Image;
	}
	public void setId_Image(int id_Image) {
		this.id_Image = id_Image;
	}
	public byte[] getContenu_Image() {
		return Contenu_Image;
	}
	public void setContenu_Image(byte[] contenu_Image) {
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
