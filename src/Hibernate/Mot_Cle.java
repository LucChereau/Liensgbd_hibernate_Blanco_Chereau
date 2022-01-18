package Hibernate;
import javax.persistence.*; 
@Entity
public class Mot_Cle {
	private String Contenu_mot_cle; 
	@ManyToOne(cascade = CascadeType.ALL)
	private Message m;
	public String getContenu_mot_cle() {
		return Contenu_mot_cle;
	}
	public void setContenu_mot_cle(String contenu_mot_cle) {
		Contenu_mot_cle = contenu_mot_cle;
	}
	public Message getM() {
		return m;
	}
	public void setM(Message m) {
		this.m = m;
	} 
}
