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
}
