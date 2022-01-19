package Hibernate;

import javax.persistence.EntityManager;

public class LienDAO {
	public static void Create_Lien(EntityManager em, String Adresse_lien, String texte, Message m) {
		Lien l=new Message(titre,texte,Date_de_post,u); 
		em.getTransaction().begin();
		em.persist(m);
		em.getTransaction().commit();
	}
}
