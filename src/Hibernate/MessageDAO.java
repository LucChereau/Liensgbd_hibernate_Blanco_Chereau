package Hibernate;

import java.util.*;
import javax.persistence.*;

public class MessageDAO {
	public static void Create_Message(EntityManager em, Utilisateur u, String titre, String texte, Date Date_de_post) {
		Message m=new Message(titre,texte,Date_de_post,u); 
		em.getTransaction().begin();
		em.persist(m);
		em.getTransaction().commit();
	}
	
	public static boolean Verify_title(EntityManager em, String titre, Utilisateur u) {
		return true; 
	}
	
	public static void Supprimer_Message(EntityManager em ,Message m,Utilisateur u) {
	boolean t=false; 
		for (int i=0; i<u.getListe_Message().size();i++) {
			if(u.getListe_Message().get(i)==m) {
				t=true; 
			}
		}
		if(t==true) {
			em.createQuery("delete from Message where id = :id")
			.setParameter("id", m.getId())
			.executeUpdate();
		}
	}
	
	public static Message GetMessage_from_title(EntityManager em, String titre) {
		String hql = "from Message m where m.titre = :titre";
		Query q = em.createQuery(hql);
		q.setParameter("titre", titre);
		List<Message> result = q.getResultList(); 
		if(result.size()==0) {
			return null; 
		}
		else {
		return result.get(0);
		}
	}
	
	public static void Modifier_Message(EntityManager em) {
		
	}
	
	
}
