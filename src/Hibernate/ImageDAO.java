package Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.type.BlobType;

public class ImageDAO {
	public static void Create_Image(EntityManager em, Message m, BlobType Contenu_Image, String Lien_serveur) {
		Image I=new Image(Contenu_Image,Lien_serveur,m); 
		em.getTransaction().begin();
		em.persist(I);
		em.getTransaction().commit();
	}
	
	public static boolean Verify_Image_from_Message(Message m , Image I ) {
		boolean t=false; 
		for (int i=0; i<m.getListe_Image().size();i++) {
			if(m.getListe_Image().get(i)==I) {
				t=true; 
			}
		}
		return t; 
	}
	
	public static void Modify_Lien_serveur_Image(EntityManager em, Message m, Image I) {
		
	}
	
	public static void Supprimer_Image(EntityManager em, Message m, Image I) {
		if(Verify_Image_from_Message(m,I)==true) {
			em.getTransaction().begin();
			em.persist(I);
			String hql="delete from Image where id_Image = :id"; 
			Query q=em.createQuery(hql); 
			q.setParameter("id", I.getId_Image()); 
			q.executeUpdate();
			em.getTransaction().commit();
		}
	}
}
