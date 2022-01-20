package Hibernate;

import javax.persistence.EntityManager;

public class LienDAO {
	public static void Create_Lien(EntityManager em, String Adresse_lien, String texte, Message m) {
		Lien l=new Lien(Adresse_lien,texte,m); 
		em.getTransaction().begin();
		em.persist(l);
		em.getTransaction().commit();
	}
	
	public static boolean Verify_Lien_From_Message(EntityManager em,Lien l, Message m ) {
		boolean t=false; 
		for (int i=0; i<m.getListe_Lien().size();i++) {
			if(m.getListe_Lien().get(i)==l) {
				t=true; 
			}
		}
		return t; 
	}
	
	public static void Modify_Adresse_Lien() {
		
	}
	
	public static void Modify_Texte_Lien() {
		
	}
	
	public static void Supprimer_Lien() {
		
	}
	
}
