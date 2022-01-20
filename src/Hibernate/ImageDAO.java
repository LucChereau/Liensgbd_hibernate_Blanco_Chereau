package Hibernate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.type.BlobType;
import com.mysql.cj.jdbc.Blob;

public class ImageDAO {
	public static void Create_Image(EntityManager em, Message m, String Lien_serveur) {
		Image I=new Image(Lien_serveur,m); 
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
	
	public static void Modify_Lien_serveur_Image(EntityManager em, Message m,Image I,String Lien_serveur) {
		if(Verify_Image_from_Message(m,I)==true) {
			Supprimer_Image(em,m,I); 
			Create_Image(em,m,Lien_serveur); 
		}
		
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
	
	public static Image GetImage_from_Adresse_serveur(EntityManager em,String Adresse_serveur, Message m ) {
		String hql = "from Image I where I.Lien_serveur = :lien_serv AND I.m= :message";
		Query q = em.createQuery(hql);
		q.setParameter("lien_serv", Adresse_serveur);
		q.setParameter("message", m); 
		List<Image> result = q.getResultList(); 
			if(result.size()==0) {
				return null; 
			}
			else {
				return result.get(0);
			}
		}
}
