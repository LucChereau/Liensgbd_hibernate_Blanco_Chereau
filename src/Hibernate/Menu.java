package Hibernate;

import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

/**
 * 
 * Classe permettant de gerer tout les menus de l'application
 *
 */
public class Menu {
	public static Scanner scanner=new Scanner(System .in );
	
	/**
	 * Methode s'occupant du menu principal de l'application
	 * @param em
	 */
	public static void Menu_Principal(EntityManager em){
		boolean menu_principal = false;
		Scanner scan2=new Scanner(System .in);
		String mail_identification = "";
		while(menu_principal == false) {
			System.out.println("Voulez-vous vous créer un compte ?");
			String creer_compte=scanner.nextLine();
			if(creer_compte.matches("oui")) {
				Menu_Create_Utilisateur(em);
				System.out.println("Votre compte à bien été créé !");
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
		
		System.out.println("Vous êtes à présent connecté !");
		
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
				Menu_Create_Message(em, utilisateur);
				break;
			case 2:
				Menu_Supprimer_Message(em, utilisateur);
				break;
			case 3:
				Menu_Modifier_Message(em, utilisateur);
				break;
			case 4:
				Affichage(em, utilisateur);
				break;
			default:
				System.out.println("Choix incorrect");
				break;
			}
			System.out.println("Souhaitez-vous rester dans le menu ?"); 
		}
	}
	
	/**
	 * Methode permettant la gestion du sous menu de creation d'utilisateur
	 * @param em
	 */
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
	
	/**
	 * Methode permettant la gestion du sous menu de creation de message
	 * @param em
	 * @param utilisateur
	 */
	public static void Menu_Create_Message(EntityManager em, Utilisateur utilisateur) {
		String titre;
		String texte;
		java.sql.Date date=java.sql.Date.valueOf(LocalDate.now()); 
		System.out.println("Veuillez saisir le titre de votre message "); 
		titre=scanner.nextLine(); 
		boolean exist = false;
		while(exist == false) {
			exist = MessageDAO.Verify_title(em, titre);
			if(exist == false) {
				System.out.println("Le titre de votre message est déjà pris, veuillez en renseigner un nouveau :");
				titre=scanner.nextLine(); 
			}
		}
		System.out.println("Veuillez saisir le corps du message "); 
		texte=scanner.nextLine(); 
		
		MessageDAO.Create_Message(em, utilisateur, titre, texte, date);
		System.out.println("Votre message à bien été créé !"); 
	}
	
	/**
	 * Methode permettant la gestion du sous menu de suppression de message
	 * @param em
	 * @param utilisateur
	 */
	public static void Menu_Supprimer_Message(EntityManager em, Utilisateur utilisateur) {
		boolean verif = false;
		String titre;
		Message message;
		System.out.println("Veuillez saisir le titre du message que vous voulez supprimer :"); 
		titre=scanner.nextLine(); 
		
		message = MessageDAO.GetMessage_from_title(em, titre);
		
		while(verif == false) {
			if (message == null) {
				System.out.println("Aucun de vos messages ne possède ce titre, veuillez saisir un titre valide :"); 
				titre=scanner.nextLine(); 
				
				message = MessageDAO.GetMessage_from_title(em, titre);
			} else {
				verif = true;
			}
		}
		
		MessageDAO.Supprimer_Message(em, message, utilisateur);
		System.out.println("Votre message à bien été supprimé");
	}
	
	/**
	 * Methode permettant la gestion du sous menu de modification de message
	 * @param em
	 * @param utilisateur
	 */
	public static void Menu_Modifier_Message(EntityManager em, Utilisateur utilisateur) {
		boolean verif = false;
		String titre;
		Message message;
		System.out.println("Veuillez saisir le titre du message que vous voulez modifier :"); 
		titre=scanner.nextLine(); 
		
		message = MessageDAO.GetMessage_from_title(em, titre);
		
		while(verif == false) {
			if (message == null) {
				System.out.println("Aucun de vos messages ne possède ce titre, veuillez saisir un titre valide :"); 
				titre=scanner.nextLine(); 
				
				message = MessageDAO.GetMessage_from_title(em, titre);
			} else {
				verif = true;
			}
		}
		Scanner scan_boucle = new Scanner(System .in );
		System.out.println("Voulez-vous modifier le message ?");
		while(scan_boucle.nextLine().matches("oui")) {
			System.out.println("-------------------------------"); 
			System.out.println("1. Modifier le Titre"); 
			System.out.println("2. Modifier le corps du message"); 
			System.out.println("3.Modifier les liens"); 
			System.out.println("4.Modifier les Images"); 
			System.out.println("5.Modifier les Mots_clés"); 
			System.out.println("-------------------------------"); 
			int choix=scanner.nextInt(); 
			   switch(choix){
			   
		       case 1: 
		           System.out.println("Saisir le nouveau Titre");
		           String titre_modif=scan_boucle.nextLine(); 
		           boolean exist = false;
			   		while(exist == false) {
			   			exist = MessageDAO.Verify_title(em, titre);
			   			if(exist == false) {
			   				System.out.println("Le titre de votre message est déjà pris, veuillez en renseigner un nouveau :");
			   				titre=scanner.nextLine(); 
			   			}
			   		}
		           MessageDAO.Modifier_Titre_Message(em, message, utilisateur, titre_modif);
		           System.out.println("Le titre a été modifié"); 
		           break;
		   
		       case 2:
		    	   System.out.println("Saisir le nouveau corps du message");
		    	   String texte_modif=scanner.nextLine(); 
		           //MessageDAO.Modifier_Texte_Message(em, message, utilisateur, texte_modif);
		           System.out.println("Le corp du message a été modifié"); 
		           break;
		   
		       case 3:
		    	   		/*Lien.modify_lien(id_message); */
		    	   		break; 
		           
		       case 4: 
		           		//Image.modify_image(id_message); 
		           		break;
		   
		       case 5:
		    	   		//Mot_Cle.modify_key_word(id_message); 
		    	   		break;
		           
		       default:
		           		System.out.println("Choix incorrect");
		           		break;
		   }
			   System.out.println("Souhaitez vous continuer a modifier le message ? ");
		}
		
		System.out.println("Votre message à bien été modifié");
	}
	
	/**
	 * Methode permettant la gestion du sous menu d'affichage des messages
	 * @param em
	 * @param utilisateur
	 */
	public static void Affichage(EntityManager em, Utilisateur utilisateur) {
		
	}
}
