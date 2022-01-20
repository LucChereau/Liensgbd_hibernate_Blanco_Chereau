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
	
	public static boolean Verify_title(EntityManager em, String titre) {
		String hql = "from Message m where m.titre = :titre";
		Query q = em.createQuery(hql);
		q.setParameter("titre", titre);
		List<Message> result = q.getResultList();
		if(result.size()==0) {
		return true; 
		}
		else {
			return false; 
		}
	}
	
	public static boolean Verify_Message_From_Utilisateur(Message m,Utilisateur u) {
		boolean t=false; 
		for (int i=0; i<u.getListe_Message().size();i++) {
			if(u.getListe_Message().get(i)==m) {
				t=true; 
			}
		}
		return t; 
	}
	
	public static void Supprimer_Message(EntityManager em ,Message m,Utilisateur u) {
		
		if(Verify_Message_From_Utilisateur(m,u)==true) {
			em.getTransaction().begin();
			em.persist(m);
			String hql="delete from Message where id = :id"; 
			Query q=em.createQuery(hql); 
			q.setParameter("id", m.getId()); 
			q.executeUpdate();
			em.getTransaction().commit();
		}
	}
	
	public static Message GetMessage_from_title(EntityManager em, String titre, Utilisateur u) {
		String hql = "from Message m where m.titre = :titre AND m.u= :utilisateur";
		Query q = em.createQuery(hql);
		q.setParameter("titre", titre);
		q.setParameter("utilisateur", u); 
		List<Message> result = q.getResultList(); 
		if(result.size()==0) {
			return null; 
		}
		else {
		return result.get(0);
		}
	}
	
	public static void Modifier_Titre_Message(EntityManager em,Message m, Utilisateur u, String new_Titre) {
		if(Verify_Message_From_Utilisateur(m,u)==true) {
			em.getTransaction().begin();
			em.persist(m);
			m.setTitre(new_Titre);
			String hql="Update Message m SET titre = :titre WHERE m.id= :id"; 
			Query q=em.createQuery(hql); 
			q.setParameter("titre", m.getTitre()); 
			q.setParameter("id", m.getId()); 
			q.executeUpdate();
			em.getTransaction().commit();
		}
	}
	public static void Modifier_Corps_Message(EntityManager em,Message m, Utilisateur u, String new_Texte) {
		if(Verify_Message_From_Utilisateur(m,u)==true) {
			em.getTransaction().begin();
			em.persist(m);
			m.setTexte(new_Texte);
			String hql="Update Message m SET texte = :texte WHERE m.id= :id"; 
			Query q=em.createQuery(hql); 
			q.setParameter("texte", m.getTexte()); 
			q.setParameter("id", m.getId()); 
			q.executeUpdate();
			em.getTransaction().commit();
		}
	}
	
	public static List<Message> get_All_Message(EntityManager em){
		Query query = em.createQuery("from Message"); 

		List<Message> list = query.getResultList();
		
		return list;
	}
}
