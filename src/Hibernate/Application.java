package Hibernate;

import javax.persistence.*;

public class Application {
	public static void main(String[] args){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
		EntityManager em = emf.createEntityManager();
		
		Menu.Menu_Principal(em);
		em.close();
		emf.close();
	}
}
