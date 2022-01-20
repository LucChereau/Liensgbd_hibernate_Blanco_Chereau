package Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class Mot_CleDAO {
	public static void Create_Mot_Cle(EntityManager em, String Contenu_Mot_Cle, Message m) {
		Mot_Cle m_c=new Mot_Cle(Contenu_Mot_Cle,m); 
		em.getTransaction().begin();
		em.persist(m_c);
		em.getTransaction().commit();
	}
	public static boolean Verify_Mot_Cle_from_Message(Message m, Mot_Cle m_c) {
		boolean t=false; 
		for (int i=0; i<m.getListe_Mot_Cle().size();i++) {
			if(m.getListe_Mot_Cle().get(i)==m_c) {
				t=true; 
			}
		}
		return t; 
	}
	
	public static void Supprimer_Mot_Cle(EntityManager em, Message m, Mot_Cle m_c) {
		if(Verify_Mot_Cle_from_Message(m,m_c)==true) {
			em.getTransaction().begin();
			em.persist(m_c);
			String hql="delete from Mot_Cle where id = :id"; 
			Query q=em.createQuery(hql); 
			q.setParameter("id", m_c.getId()); 
			q.executeUpdate();
			em.getTransaction().commit();
		}
	}
}
