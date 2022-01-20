package Hibernate;

import java.text.ParseException;

import javax.persistence.*;

public class Application {
	public static void main(String[] args) throws ParseException{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
		EntityManager em = emf.createEntityManager();
		
		Menu.Menu_Principal(em);
		em.close();
		emf.close();
	}
}
