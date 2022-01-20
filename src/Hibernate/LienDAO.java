package Hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class LienDAO {
	public static void Create_Lien(EntityManager em, String Adresse_lien, String texte, Message m) {
		Lien l=new Lien(Adresse_lien,texte,m); 
		em.getTransaction().begin();
		em.persist(l);
		em.getTransaction().commit();
	}
	
	
	public static boolean Verify_Lien_From_Message(Message m,Lien l) {
		boolean t=false; 
		for (int i=0; i<m.getListe_Lien().size();i++) {
			if(m.getListe_Lien().get(i)==l) {
				t=true; 
			}
		}
		return t; 
	}
	
	public static void Modify_Adresse_Lien(EntityManager em, Lien l, Message m, String new_Adresse_Lien) {
		if(Verify_Lien_From_Message(m,l)==true) {
			em.getTransaction().begin();
			em.persist(l);
			l.setAdresse_Lien(new_Adresse_Lien);
			String hql="Update Lien l SET adresse_Lien = :adresse_Lien WHERE l.id_lien= :id"; 
			Query q=em.createQuery(hql); 
			q.setParameter("adresse_Lien", l.getAdresse_Lien()); 
			q.setParameter("id", l.getId_lien()); 
			q.executeUpdate();
			em.getTransaction().commit();
		}
	}
	
	public static void Modify_Texte_Lien(EntityManager em, Message m, Lien l, String new_Texte_Lien) {
		if(Verify_Lien_From_Message(m,l)==true) {
			em.getTransaction().begin();
			em.persist(l);
			l.setAdresse_Lien(new_Texte_Lien);
			String hql="Update Lien l SET texte = :texte WHERE l.id_lien= :id"; 
			Query q=em.createQuery(hql); 
			q.setParameter("texte", l.getTexte()); 
			q.setParameter("id", l.getId_lien()); 
			q.executeUpdate();
			em.getTransaction().commit();
		}
	}
	
	public static void Supprimer_Lien(EntityManager em, Message m, Lien l) {
		if(Verify_Lien_From_Message(m,l)==true) {
			em.getTransaction().begin();
			em.persist(l);
			String hql="delete from Lien where id_lien = :id"; 
			Query q=em.createQuery(hql); 
			q.setParameter("id", l.getId_lien()); 
			q.executeUpdate();
			em.getTransaction().commit();
		}
	}
	
	public static Lien GetLien_grom_Adresse_Lien(EntityManager em, String adresse_lien, Message m) {
		String hql = "from Lien l where l.adresse_Lien = :adresse AND l.m= :message";
		Query q = em.createQuery(hql);
		q.setParameter("adresse", adresse_lien);
		q.setParameter("message", m); 
		List<Lien> result = q.getResultList(); 
		if(result.size()==0) {
			return null; 
		}
		else {
		return result.get(0);
		}
	}
	
}
