package Hibernate;

import javax.persistence.EntityManager;

public class LienDAO {
	public static void Create_Lien(EntityManager em, String Adresse_lien, String texte, Message m) {
		Lien l=new Lien(Adresse_lien,texte,m); 
		em.getTransaction().begin();
		em.persist(l);
		em.getTransaction().commit();
	}
}
