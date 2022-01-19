package Hibernate;

import javax.persistence.*;

public class Application {
	public static void main(String[] args){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Test");
		EntityManager em = emf.createEntityManager();
		
		Menu.Menu_Principal(em);
	}
}
