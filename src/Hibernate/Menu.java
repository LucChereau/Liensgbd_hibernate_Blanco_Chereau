package Hibernate;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



public class Menu {
	public static Scanner scanner=new Scanner(System .in );
	
	public static void Menu_Principal(){
		Scanner scan2=new Scanner(System .in);
		System.out.println("Voulez-vous vous créer un compte ?");
		String creer_compte=scanner.nextLine();
		if(creer_compte.matches("oui")) {
			UtilisateurDAO.Create_Utilisateur();
		}
		boolean exist = false;
		while(exist == false) {
			System.out.println("Votre mail :");
			String mail_identification=scanner.nextLine();
			
			exist = UtilisateurDAO.Recherche_Mail(mail_identification);
			if(exist == false) {
				System.out.println("Erreur sur votre mail, veuillez recommencer !");
			}
		}
		
		System.out.println("Votre mot de passe :");
		String mot_de_passe=scan2.nextLine();
		
		
		while(sql_mdp.matches(mot_de_passe) != true) {
			System.out.println("Votre mot de passe n'est pas correct, veuillez saisir de nouveau vos données :");
			
			System.out.println("Votre mail :");
			mail_identification=scanner.nextLine();
			mail_identification = Recherche_mail(mail_identification);
			rs_id_user = stmt.executeQuery("SELECT * FROM Utilisateur WHERE Mail = '"+mail_identification+"';");
			rs_id_user.next();
			id_utilisateur = rs_id_user.getInt(1);
			sql_mdp = rs_id_user.getString(6);
			
			System.out.println("Veuillez saisir votre mot de passe :");
			mot_de_passe=scanner.nextLine();
			rs_id_user = stmt.executeQuery("SELECT * FROM Utilisateur WHERE Id_Utilisateur = "+id_utilisateur+";");
			rs_id_user.next();
			id_utilisateur = rs_id_user.getInt(1);
			sql_mdp = rs_id_user.getString(6);
			
		}
		
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
				Message.post_message(id_utilisateur);
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
