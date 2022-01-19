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
	
	public static void Supprimer_Message(EntityManager em) {
		
	}
	
	public static void Modifier_Message(EntityManager em) {
		
	}
	
	
}
