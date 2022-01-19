package Hibernate;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.persistence.EntityManager;



public class Menu {
	public static Scanner scanner=new Scanner(System .in );
	
	public static void Menu_Principal(EntityManager em){
		boolean menu_principal = false;
		Scanner scan2=new Scanner(System .in);
		String mail_identification;
		while(menu_principal == false) {
			System.out.println("Voulez-vous vous créer un compte ?");
			String creer_compte=scanner.nextLine();
			if(creer_compte.matches("oui")) {
				//UtilisateurDAO.Create_Utilisateur(em);
			}
			
			System.out.println("Votre mail :");
			mail_identification=scanner.nextLine();
			menu_principal = UtilisateurDAO.Recherche_Mail(em, mail_identification);
			if(menu_principal == false) {
				System.out.println("Erreur sur votre mail, veuillez recommencer !");
			}
		}
		
		boolean exist = false;
		
		System.out.println("Votre mot de passe :");
		String mot_de_passe=scan2.nextLine();
		
		exist = UtilisateurDAO.Compare_Mdp(em, mail_identification, mot_de_passe);
		
		while(exist == false) {
			System.out.println("Votre mot de passe n'est pas correct, veuillez saisir de nouveau vos données :");
			
			System.out.println("Votre mail :");
			mail_identification=scanner.nextLine();
			while(exist == false) {
				exist = UtilisateurDAO.Recherche_Mail(em, mail_identification);
				if(exist == false) {
					System.out.println("Erreur sur votre mail, veuillez recommencer !");
				}
			}
			
			System.out.println("Votre mot de passe :");
			mot_de_passe=scan2.nextLine();
			
			exist = UtilisateurDAO.Compare_Mdp(em, mail_identification, mot_de_passe);
		}
		
		Utilisateur utilisateur = UtilisateurDAO.get_Utilisateur(em, mail_identification, mot_de_passe);
		
		int id_utilisateur = utilisateur.getId();
		
		Scanner scanner_boucle=new Scanner(System .in);
		System.out.println("Souhaitez-vous allez dans le menu ?");
		while(scanner.nextLine().matches("oui")) {
			System.out.println("-------------------------------"); 
			System.out.println("1. Publier un message"); 
			System.out.println("2. Supprimer un message"); 
			System.out.println("3. Modifier un message"); 
			System.out.println("4. Afficher des messages"); 
			System.out.println("-------------------------------"); 
			
			int choix = scanner_boucle.nextInt();
			switch(choix) {
			case 1:
				MessageDAO.Create_Message(em);
				break;
			case 2:
				Message.del_message(id_utilisateur);
				break;
			case 3:
				Message.modify_message(id_utilisateur);
				break;
			case 4:
				Affichage(id_utilisateur);
				break;
			default:
				System.out.println("Choix incorrect");
				break;
			}
			System.out.println("Souhaitez-vous rester dans le menu ?"); 
		}
	}
}
