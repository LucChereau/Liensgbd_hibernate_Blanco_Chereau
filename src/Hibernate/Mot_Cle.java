package Hibernate;
import javax.persistence.*; 
@Entity
public class Mot_Cle {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String Contenu_mot_cle; 
	@ManyToOne(cascade = CascadeType.ALL)
	private Message m;
	
	public Mot_Cle() {
		Contenu_mot_cle=null;
	}
	
	public Mot_Cle(String Contenu_Mot_Cle, Message m) {
		this.Contenu_mot_cle=Contenu_Mot_Cle; 
		this.m=m; 
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
