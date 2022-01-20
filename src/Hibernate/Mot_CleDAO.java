package Hibernate;

import javax.persistence.EntityManager;

public class Mot_CleDAO {
	public static void Create_Mot_Cle(EntityManager em, String Contenu_Mot_Cle, Message m) {
		Mot_Cle m_c=new Mot_Cle(Contenu_Mot_Cle,m); 
		em.getTransaction().begin();
		em.persist(m_c);
		em.getTransaction().commit();
	}
}
