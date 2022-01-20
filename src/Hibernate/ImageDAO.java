package Hibernate;

import javax.persistence.EntityManager;

import org.hibernate.type.BlobType;

public class ImageDAO {
	public void Create_Image(EntityManager em, Message m, BlobType Contenu_Image, String Lien_serveur) {
		Image I=new Image(Contenu_Image,Lien_serveur,m); 
		em.getTransaction().begin();
		em.persist(I);
		em.getTransaction().commit();
	}
}
