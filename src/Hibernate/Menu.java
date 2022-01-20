package Hibernate;

import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import org.hibernate.type.BlobType;

import com.mysql.cj.jdbc.Blob;

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
		do  {
			exist = MessageDAO.Verify_title(em, titre);
			if(exist == false) {
				System.out.println("Le titre de votre message est déjà pris, veuillez en renseigner un nouveau :");
				titre=scanner.nextLine(); 
			}
		}while(exist == false); 
		System.out.println("Veuillez saisir le corps du message "); 
		texte=scanner.nextLine(); 
		
		MessageDAO.Create_Message(em, utilisateur, titre, texte, date);
		Message message = MessageDAO.GetMessage_from_title(em, titre,utilisateur);
		Menu_Create_Lien(em, utilisateur, message);
		//Menu_Create_Image(em, utilisateur, message);
		Menu_Create_Mot_Cle(em, utilisateur, message);
		System.out.println("Votre message à bien été créé !"); 
	}
	
	/**
	 * 
	 * @param em
	 * @param utilisateur
	 * @param message
	 */
	public static void Menu_Create_Lien(EntityManager em, Utilisateur utilisateur, Message message) {
		System.out.println("Souhaitez-vous ajouter un lien à votre message ?");
		String oui = scanner.nextLine();
		while(oui.matches("oui")) {
			System.out.println("Veuillez saisir l'adresse URL de votre lien :");
			String lien_txt = scanner.nextLine();
			boolean exist = false;
			do  {
				if(LienDAO.GetLien_from_Adresse_Lien(em, lien_txt, message) != null) {
					exist = false;
				} else {
					exist = true;
				}
				if(exist == false) {
					System.out.println("Ce lien est déjà utilisé dans votre message,"
							+ " veuillez en renseigner un nouveau :");
					lien_txt=scanner.nextLine(); 
				}
			}while(exist == false); 
			
			System.out.println("Veuillez saisir un descriptif de celui-ci :");
			String desc = scanner.nextLine();
			LienDAO.Create_Lien(em, lien_txt, desc, message);
			System.out.println("Votre lien à bien été ajouté !");
			
			System.out.println("Voulez vous ajouter un autre lien à votre message ?");
			oui = scanner.nextLine();
		}
	}
	
	/**
	 * 
	 * @param em
	 * @param utilisateur
	 * @param message
	 */
	/*public static void Menu_Create_Image(EntityManager em, Utilisateur utilisateur, Message message) {
		System.out.println("Souhaitez-vous ajouter une image à votre message ?");
		String oui = scanner.nextLine();
		while(oui.matches("oui")) {
			System.out.println("Veuillez saisir le parcours serveur pour l'image :");
			String parcours = scanner.nextLine();
			boolean exist = false;
			do  {
				if(ImageDAO.GetImage_from_Adresse_serveur(em, parcours, message) != null) {
					exist = false;
				} else {
					exist = true;
				}
				if(exist == false) {
					System.out.println("Cette image est déjà utilisé dans votre message,"
							+ " veuillez en renseigner une nouvelle :");
					parcours=scanner.nextLine(); 
				}
			}while(exist == false); 
			
			Blob image = new Blob();
			
			ImageDAO.Create_Image(em, message, image, parcours);
			System.out.println("Votre image à bien été ajouté !");
			
			System.out.println("Voulez vous ajouter une autre image à votre message ?");
			oui = scanner.nextLine();
		}
	}*/
	
	/**
	 * 
	 * @param em
	 * @param utilisateur
	 * @param message
	 */
	public static void Menu_Create_Mot_Cle(EntityManager em, Utilisateur utilisateur, Message message) {
		System.out.println("Souhaitez-vous ajouter un mot_clé à votre message ?");
		String oui = scanner.nextLine();
		while(oui.matches("oui")) {
			System.out.println("Veuillez saisir un mot clé :");
			String mot_cle = scanner.nextLine();
			boolean exist = false;
			do  {
				if(Mot_CleDAO.GetMot_Cle_from_Contenu(em, mot_cle, message) != null) {
					exist = false;
				} else {
					exist = true;
				}
				if(exist == false) {
					System.out.println("Ce mot_clé est déjà utilisé dans votre message,"
							+ " veuillez en renseigner un nouveau :");
					mot_cle=scanner.nextLine(); 
				}
			}while(exist == false); 
			
			Mot_CleDAO.Create_Mot_Cle(em, mot_cle, message);
			System.out.println("Votre mot-clé à bien été ajouté !");
			
			System.out.println("Voulez vous ajouter un autre mot-clé à votre message ?");
			oui = scanner.nextLine();
		}
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
		
		message = MessageDAO.GetMessage_from_title(em, titre,utilisateur);
		
		while(verif == false) {
			if (message == null) {
				System.out.println("Aucun de vos messages ne possède ce titre, veuillez saisir un titre valide :"); 
				titre=scanner.nextLine(); 
				
				message = MessageDAO.GetMessage_from_title(em, titre,utilisateur);
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
		
		message = MessageDAO.GetMessage_from_title(em, titre,utilisateur);
		
		while(verif == false) {
			if (message == null) {
				System.out.println("Aucun de vos messages ne possède ce titre, veuillez saisir un titre valide :"); 
				titre=scanner.nextLine(); 
				
				message = MessageDAO.GetMessage_from_title(em, titre,utilisateur);
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
			System.out.println("3. Modifier les liens"); 
			System.out.println("4. Modifier les Images"); 
			System.out.println("5. Modifier les Mots_clés"); 
			System.out.println("-------------------------------"); 
			int choix=scanner.nextInt(); 
			switch(choix){
		       case 1: 
		           System.out.println("Saisir le nouveau Titre");
		           String titre_modif=scan_boucle.nextLine(); 
		           boolean exist = false;
			   		while(exist == false) {
			   			exist = MessageDAO.Verify_title(em, titre_modif);
			   			if(exist == false) {
			   				System.out.println("Le titre de votre message est déjà pris, veuillez en renseigner un nouveau :");
			   				titre_modif=scanner.nextLine(); 
			   			}
			   		}
		           MessageDAO.Modifier_Titre_Message(em, message, utilisateur, titre_modif);
		           System.out.println("Le titre a été modifié"); 
		           break;
		   
		       case 2:
		    	   System.out.println("Saisir le nouveau corps du message");
		    	   String texte_modif=scan_boucle.nextLine(); 
		           MessageDAO.Modifier_Corps_Message(em, message, utilisateur, texte_modif);
		           System.out.println("Le corp du message a été modifié"); 
		           break;
		   
		       case 3:
		    	   Menu_Modify_Lien(em, utilisateur, message);
		    	   break; 
		           
		       case 4: 
		    	   Menu_Modify_Image(em, utilisateur, message); 
		           break;
		   
		       case 5:
		    	   Menu_Modify_Mot_Cle(em, utilisateur, message); 
		    	   break;
		           
		       default:
		           System.out.println("Choix incorrect");
		           break;
			}
			System.out.println("Souhaitez vous continuer a modifier le message ? ");
		}
		
		System.out.println("Votre message à bien été modifié");
	}
	
	public static void Menu_Modify_Lien(EntityManager em, Utilisateur utilisateur, Message message) {
		System.out.println("Souhaitez-vous modifier, supprimer ou ajouter un lien à votre message ?");
		String oui = scanner.nextLine();
		while(oui.matches("oui")) {
			System.out.println("-------------------------------"); 
			System.out.println("1. Modifier un lien"); 
			System.out.println("2. Modifier un descriptif"); 
			System.out.println("3. Supprimer un lien"); 
			System.out.println("4. Ajouter un lien"); 
			System.out.println("-------------------------------"); 
			
			int choix = scanner.nextInt();
			Scanner scan_boucle = new Scanner(System .in );
			switch(choix) {
			case 1:
				System.out.println("Saisir le lien que vous voulez modifier :");
		    	String ancien_lien=scan_boucle.nextLine(); 
		    	
		    	boolean exist = false;
				do  {
					if(LienDAO.GetLien_from_Adresse_Lien(em, ancien_lien, message) == null) {
						exist = false;
					} else {
						exist = true;
					}
					if(exist == false) {
						System.out.println("Ce lien n'est pas utilisé dans votre message,"
								+ " veuillez en renseigner un nouveau :");
						ancien_lien=scanner.nextLine(); 
					}
				}while(exist == false);
		    	
				Lien lien = LienDAO.GetLien_from_Adresse_Lien(em, ancien_lien, message);
				
		    	System.out.println("Saisir le nouveau lien que vous voulez ajouter :");
		    	String new_lien=scan_boucle.nextLine(); 
		        LienDAO.Modify_Adresse_Lien(em, lien, message, new_lien);
		        System.out.println("Le lien a été modifié"); 
				break;
			case 2:
				System.out.println("Saisir le lien que vous voulez modifier :");
		    	String lien_txt2 = scan_boucle.nextLine(); 
		    	boolean exist2 = false;
				do  {
					if(LienDAO.GetLien_from_Adresse_Lien(em, lien_txt2, message) == null) {
						exist2 = false;
					} else {
						exist2 = true;
					}
					if(exist2 == false) {
						System.out.println("Ce lien n'est pas utilisé dans votre message,"
								+ " veuillez en renseigner un nouveau :");
						lien_txt2=scanner.nextLine(); 
					}
				}while(exist2 == false);
		    	
				Lien lien2 = LienDAO.GetLien_from_Adresse_Lien(em, lien_txt2, message);
				
		    	System.out.println("Saisir le nouveau descriptif que vous voulez ajouter :");
		    	String new_desc=scan_boucle.nextLine(); 
		        LienDAO.Modify_Texte_Lien(em, message, lien2, new_desc);
		        System.out.println("Le descriptif du lien a été modifié"); 
				break;
			case 3:
				System.out.println("Saisir le lien que vous voulez supprimer :");
		    	String lien_txt3=scan_boucle.nextLine(); 
		    	boolean exist3 = false;
				do  {
					if(LienDAO.GetLien_from_Adresse_Lien(em, lien_txt3, message) == null) {
						exist3 = false;
					} else {
						exist3 = true;
					}
					if(exist3 == false) {
						System.out.println("Ce lien n'est pas utilisé dans votre message,"
								+ " veuillez en renseigner un nouveau :");
						lien_txt3=scanner.nextLine(); 
					}
				}while(exist3 == false);
		    	
		    	Lien lien3 = LienDAO.GetLien_from_Adresse_Lien(em, lien_txt3, message);
		    	
		    	LienDAO.Supprimer_Lien(em, message, lien3);
				break;
			case 4:
				Menu_Create_Lien(em, utilisateur, message);
				break;
			default:
				System.out.println("Choix incorrect");
				break;
			}
			System.out.println("Souhaitez-vous rester dans le menu ?"); 
			oui = scanner.nextLine();
		}
	}
	
	//pas fait
	public static void Menu_Modify_Image(EntityManager em, Utilisateur utilisateur, Message message) {
		System.out.println("Souhaitez-vous modifier, supprimer ou ajouter une image à votre message ?");
		String oui = scanner.nextLine();
		while(oui.matches("oui")) {
			System.out.println("-------------------------------"); 
			System.out.println("1. Modifier une image"); 
			System.out.println("2. Supprimer une image"); 
			System.out.println("3. Ajouter une image"); 
			System.out.println("-------------------------------"); 
			
			int choix = scanner.nextInt();
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
			default:
				System.out.println("Choix incorrect");
				break;
			}
			System.out.println("Souhaitez-vous rester dans le menu ?"); 
			oui = scanner.nextLine();
		}
	}
	
	public static void Menu_Modify_Mot_Cle(EntityManager em, Utilisateur utilisateur, Message message) {
		System.out.println("Souhaitez-vous modifier, supprimer ou ajouter un mot-clé à votre message ?");
		String oui = scanner.nextLine();
		while(oui.matches("oui")) {
			System.out.println("-------------------------------"); 
			System.out.println("1. Modifier un mot-clé"); 
			System.out.println("2. Supprimer un mot-clé"); 
			System.out.println("3. Ajouter un mot-clé"); 
			System.out.println("-------------------------------"); 
			Scanner scan_boucle = new Scanner(System .in );
			int choix = scanner.nextInt();
			switch(choix) {
			case 1:
				System.out.println("Saisir le mot-clé que vous voulez modifier :");
		    	String mot_cle_contenu=scan_boucle.nextLine(); 
		    	
		    	boolean exist = false;
				do  {
					if(Mot_CleDAO.GetMot_Cle_from_Contenu(em, mot_cle_contenu, message) == null) {
						exist = false;
					} else {
						exist = true;
					}
					if(exist == false) {
						System.out.println("Ce mot-clé n'est pas utilisé dans votre message,"
								+ " veuillez en renseigner un nouveau :");
						mot_cle_contenu=scanner.nextLine(); 
					}
				}while(exist == false);
		    	
				Mot_Cle mot_cle = Mot_CleDAO.GetMot_Cle_from_Contenu(em, mot_cle_contenu, message);
				
		    	System.out.println("Saisir le nouveau mot-clé que vous voulez ajouter :");
		    	String new_mot_cle=scan_boucle.nextLine(); 
		        Mot_CleDAO.Modify_Contenu_mot_cle(em, message, mot_cle, new_mot_cle);
		        System.out.println("Le mot-clé a été modifié"); 
				break;
			case 2:
				System.out.println("Saisir le mot-clé que vous voulez supprimer :");
		    	String mot_cle_suppr=scan_boucle.nextLine(); 
		    	
		    	boolean exist2 = false;
				do  {
					if(Mot_CleDAO.GetMot_Cle_from_Contenu(em, mot_cle_suppr, message) == null) {
						exist2 = false;
					} else {
						exist2 = true;
					}
					if(exist2 == false) {
						System.out.println("Ce mot-clé n'est pas utilisé dans votre message,"
								+ " veuillez en renseigner un nouveau :");
						mot_cle_suppr=scanner.nextLine(); 
					}
				}while(exist2 == false);
		    	
				Mot_Cle mot_cle2 = Mot_CleDAO.GetMot_Cle_from_Contenu(em, mot_cle_suppr, message);
				
		        Mot_CleDAO.Supprimer_Mot_Cle(em, message, mot_cle2);
		        System.out.println("Le mot-clé a été supprimé");
				break;
			case 3:
				Menu.Menu_Create_Mot_Cle(em, utilisateur, message);
				break;
			default:
				System.out.println("Choix incorrect");
				break;
			}
			System.out.println("Souhaitez-vous rester dans le menu ?"); 
			oui = scanner.nextLine();
		}
	}
	
	/**
	 * Methode permettant la gestion du sous menu d'affichage des messages
	 * @param em
	 * @param utilisateur
	 */
	public static void Affichage(EntityManager em, Utilisateur utilisateur) {
		
	}
}
