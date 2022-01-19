package Hibernate;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;



public class Menu {
	public static Scanner scanner=new Scanner(System .in );
	
	public static void Menu_Principal(EntityManager em){
		boolean menu_principal = false;
		Scanner scan2=new Scanner(System .in);
		String mail_identification = "";
		while(menu_principal == false) {
			System.out.println("Voulez-vous vous créer un compte ?");
			String creer_compte=scanner.nextLine();
			if(creer_compte.matches("oui")) {
				Menu_Create_Utilisateur(em);
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
		
		Utilisateur utilisateur = UtilisateurDAO.get_Utilisateur(em, mail_identification);
		
		exist = UtilisateurDAO.Compare_Mdp(em, utilisateur, mot_de_passe);
		
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
			
			utilisateur = UtilisateurDAO.get_Utilisateur(em, mail_identification);
			
			exist = UtilisateurDAO.Compare_Mdp(em, utilisateur, mot_de_passe);
		}
		
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
				Menu_Create_Message(em);
				break;
			case 2:
				Menu_Supprimer_Message(em);
				break;
			case 3:
				Menu_Modifier_Message(em);
				break;
			case 4:
				Affichage();
				break;
			default:
				System.out.println("Choix incorrect");
				break;
			}
			System.out.println("Souhaitez-vous rester dans le menu ?"); 
		}
	}
	
	public static void Menu_Create_Utilisateur(EntityManager em) {
		String nom;
		String prenom;
		String mail = "";
		String mot_de_passe;
		String city;
		System.out.println("Veuillez saisir le Nom de L'utilisateur"); 
		nom=scanner.nextLine(); 
		System.out.println("Veuillez saisir le prenom de L'utilisateur");
		prenom=scanner.nextLine(); 
		System.out.println("Veuillez saisir la ville de L'utilisateur");
		city=scanner.nextLine(); 
		boolean format_email=false; 
		Pattern rfc2822 = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
		while(format_email==false) {
			System.out.println("Veuillez saisir une adresse email valide");
			mail=scanner.nextLine(); 
			if (rfc2822.matcher(mail).matches()) {
				format_email=true; 
			}
			if(UtilisateurDAO.Recherche_Mail(em, mail) == true) {
				format_email = false;
				System.out.println("Cet email est déjà utilisé");
			}
		}
		System.out.println("Veuillez saisir le mot de passe de l'utilisateur");
		mot_de_passe=scanner.nextLine(); 
		
		UtilisateurDAO.Create_Utilisateur(em, nom, prenom, mail, mot_de_passe, city);
	}
	
	public static void Menu_Create_Message(EntityManager em) {
		
	}
	
	public static void Menu_Supprimer_Message(EntityManager em) {
		
	}
	
	public static void Menu_Modifier_Message(EntityManager em) {
		
	}
	
	public static void Affichage() {
		
	}
}
