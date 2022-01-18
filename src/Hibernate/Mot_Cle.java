package Hibernate;
import javax.persistence.*; 
@Entity
public class Mot_Cle {
	private String Contenu_mot_cle; 
	@ManyToOne(cascade = CascadeType.ALL)
	private Message m; 
}
