package Hibernate;

import java.util.List;

import javax.persistence.*;


public class UtilisateurDAO {
	public static void Create_Utilisateur(EntityManager em) {
		
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
		Utilisateur result = q.getFirstResult();

		return result;
	}
}
