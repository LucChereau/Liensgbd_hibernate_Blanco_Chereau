package Hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


public class UtilisateurDAO {
	public static Utilisateur Create_Utilisateur(EntityManager em, String nom,String prenom, String mail, String mot_de_passe, String city) {
		Adresse a = new Adresse();
		a.setCity(city);
		Utilisateur u=new Utilisateur(nom,prenom,mail,mot_de_passe,a); 
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
		return u; 
	}
	
	public static boolean Recherche_Mail(EntityManager em, String mail) {
		boolean result = true;
		String hql = "from Utilisateur u where u.mail = :mail";
		Query q = em.createQuery(hql);
		q.setParameter("mail", mail);
		List<Utilisateur> liste = q.getResultList();
		int size = liste.size();
		if (size == 0) {
			result = false;
		}
		return result;
	}
	
	public static boolean Compare_Mdp(EntityManager em, String mail, String mdp) {
		boolean result = true;
		return result;
	}
	
	public static Utilisateur get_Utilisateur(EntityManager em, String mail, String mdp) {
		String hql = "from Utilisateur u where u.mail = :mail AND u.mot_de_passe = :mdp";
		Query q = em.createQuery(hql);
		q.setParameter("mail", mail);
		q.setParameter("mot_de_passe", mdp);
		List<Utilisateur> result = q.getResultList(); 
		return result.get(0);
	}
}
