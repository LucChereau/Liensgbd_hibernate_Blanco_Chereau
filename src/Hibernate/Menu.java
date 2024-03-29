package Hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	 * @throws ParseException 
	 */
	public static void Menu_Principal(EntityManager em) throws ParseException{
		boolean menu_principal = false;
		Scanner scan2=new Scanner(System .in);
		String mail_identification = "";
		while(menu_principal == false) {
			System.out.println("Voulez-vous vous cr�er un compte ?");
			String creer_compte=scanner.nextLine();
			if(creer_compte.matches("oui")) {
				Menu_Create_Utilisateur(em);
				System.out.println("Votre compte � bien �t� cr�� !");
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
			System.out.println("Votre mot de passe n'est pas correct, veuillez saisir de nouveau vos donn�es :");
			
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
		
		System.out.println("Vous �tes � pr�sent connect� !");
		Scanner scanner_boucle = new Scanner(System .in );
		System.out.println("Souhaitez-vous allez dans le menu ?");
		String reponse = scanner.nextLine();
		while(reponse.matches("oui")) {
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
				Menu_Affichage_Message(em, utilisateur);
				break;
			default:
				System.out.println("Choix incorrect");
				break;
			}
			System.out.println("Souhaitez-vous rester dans le menu ?"); 
			scanner_boucle.nextLine();
			reponse = scanner_boucle.nextLine();
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
				System.out.println("Cet email est d�j� utilis�");
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
				System.out.println("Le titre de votre message est d�j� pris, veuillez en renseigner un nouveau :");
				titre=scanner.nextLine(); 
			}
		}while(exist == false); 
		System.out.println("Veuillez saisir le corps du message "); 
		texte=scanner.nextLine(); 
		
		MessageDAO.Create_Message(em, utilisateur, titre, texte, date);
		Message message = MessageDAO.GetMessage_from_title(em, titre,utilisateur);
		Menu_Create_Lien(em, utilisateur, message);
		Menu_Create_Image(em, utilisateur, message);
		Menu_Create_Mot_Cle(em, utilisateur, message);
		System.out.println("Votre message � bien �t� cr�� !"); 
	}
	
	/**
	 * 
	 * @param em
	 * @param utilisateur
	 * @param message
	 */
	public static void Menu_Create_Lien(EntityManager em, Utilisateur utilisateur, Message message) {
		System.out.println("Souhaitez-vous ajouter un lien � votre message ?");
		scanner.nextLine();
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
					System.out.println("Ce lien est d�j� utilis� dans votre message,"
							+ " veuillez en renseigner un nouveau :");
					lien_txt=scanner.nextLine(); 
				}
			}while(exist == false); 
			
			System.out.println("Veuillez saisir un descriptif de celui-ci :");
			String desc = scanner.nextLine();
			LienDAO.Create_Lien(em, lien_txt, desc, message);
			System.out.println("Votre lien � bien �t� ajout� !");
			
			System.out.println("Voulez vous ajouter un autre lien � votre message ?");
			oui = scanner.nextLine();
		}
	}
	
	/**
	 * 
	 * @param em
	 * @param utilisateur
	 * @param message
	 */
	public static void Menu_Create_Image(EntityManager em, Utilisateur utilisateur, Message message) {
		System.out.println("Souhaitez-vous ajouter une image � votre message ?");
		scanner.nextLine();
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
					System.out.println("Cette image est d�j� utilis� dans votre message,"
							+ " veuillez en renseigner une nouvelle :");
					parcours=scanner.nextLine(); 
				}
			}while(exist == false); 
		
			
			ImageDAO.Create_Image(em, message,parcours);
			System.out.println("Votre image � bien �t� ajout� !");
			
			System.out.println("Voulez vous ajouter une autre image � votre message ?");
			oui = scanner.nextLine();
		}
	}
	
	/**
	 * 
	 * @param em
	 * @param utilisateur
	 * @param message
	 */
	public static void Menu_Create_Mot_Cle(EntityManager em, Utilisateur utilisateur, Message message) {
		System.out.println("Souhaitez-vous ajouter un mot_cl� � votre message ?");
		scanner.nextLine();
		String oui = scanner.nextLine();
		while(oui.matches("oui")) {
			System.out.println("Veuillez saisir un mot cl� :");
			String mot_cle = scanner.nextLine();
			boolean exist = false;
			do  {
				if(Mot_CleDAO.GetMot_Cle_from_Contenu(em, mot_cle, message) != null) {
					exist = false;
				} else {
					exist = true;
				}
				if(exist == false) {
					System.out.println("Ce mot_cl� est d�j� utilis� dans votre message,"
							+ " veuillez en renseigner un nouveau :");
					mot_cle=scanner.nextLine(); 
				}
			}while(exist == false); 
			
			Mot_CleDAO.Create_Mot_Cle(em, mot_cle, message);
			System.out.println("Votre mot-cl� � bien �t� ajout� !");
			
			System.out.println("Voulez vous ajouter un autre mot-cl� � votre message ?");
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
				System.out.println("Aucun de vos messages ne poss�de ce titre, veuillez saisir un titre valide :"); 
				titre=scanner.nextLine(); 
				
				message = MessageDAO.GetMessage_from_title(em, titre,utilisateur);
			} else {
				verif = true;
			}
		}
		
		MessageDAO.Supprimer_Message(em, message, utilisateur);
		System.out.println("Votre message � bien �t� supprim�");
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
		scanner.nextLine(); 
		titre=scanner.nextLine(); 
		
		message = MessageDAO.GetMessage_from_title(em, titre,utilisateur);
		
		while(verif == false) {
			if (message == null) {
				System.out.println("Aucun de vos messages ne poss�de ce titre, veuillez saisir un titre valide :"); 
				titre=scanner.nextLine(); 
				
				message = MessageDAO.GetMessage_from_title(em, titre,utilisateur);
			} else {
				verif = true;
			}
		}
		System.out.println("Voulez-vous modifier le message ?");
		String oui = scanner.nextLine();
		while(oui.matches("oui")) {
			System.out.println("-------------------------------"); 
			System.out.println("1. Modifier le Titre"); 
			System.out.println("2. Modifier le corps du message"); 
			System.out.println("3. Modifier les liens"); 
			System.out.println("4. Modifier les Images"); 
			System.out.println("5. Modifier les Mots_cl�s"); 
			System.out.println("-------------------------------"); 
			int choix=scanner.nextInt(); 
			Scanner scan_boucle = new Scanner(System .in );
			switch(choix){
		       case 1: 
		           System.out.println("Saisir le nouveau Titre");
		           String titre_modif=scan_boucle.nextLine(); 
		           boolean exist = false;
			   		while(exist == false) {
			   			exist = MessageDAO.Verify_title(em, titre_modif);
			   			if(exist == false) {
			   				System.out.println("Le titre de votre message est d�j� pris, veuillez en renseigner un nouveau :");
			   				titre_modif=scan_boucle.nextLine(); 
			   			}
			   		}
		           MessageDAO.Modifier_Titre_Message(em, message, utilisateur, titre_modif);
		           System.out.println("Le titre a �t� modifi�"); 
		           break;
		   
		       case 2:
		    	   System.out.println("Saisir le nouveau corps du message");
		    	   String texte_modif=scan_boucle.nextLine(); 
		           MessageDAO.Modifier_Corps_Message(em, message, utilisateur, texte_modif);
		           System.out.println("Le corp du message a �t� modifi�"); 
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
			oui=scan_boucle.nextLine();
		}
	}
	
	public static void Menu_Modify_Lien(EntityManager em, Utilisateur utilisateur, Message message) {
		System.out.println("Souhaitez-vous modifier, supprimer ou ajouter un lien � votre message ?");
		scanner.nextLine();
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
						System.out.println("Ce lien n'est pas utilis� dans votre message,"
								+ " veuillez en renseigner un nouveau :");
						ancien_lien=scanner.nextLine(); 
					}
				}while(exist == false);
		    	
				Lien lien = LienDAO.GetLien_from_Adresse_Lien(em, ancien_lien, message);
				
		    	System.out.println("Saisir le nouveau lien que vous voulez ajouter :");
		    	String new_lien=scan_boucle.nextLine(); 
		        LienDAO.Modify_Adresse_Lien(em, lien, message, new_lien);
		        System.out.println("Le lien a �t� modifi�"); 
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
						System.out.println("Ce lien n'est pas utilis� dans votre message,"
								+ " veuillez en renseigner un nouveau :");
						lien_txt2=scanner.nextLine(); 
					}
				}while(exist2 == false);
		    	
				Lien lien2 = LienDAO.GetLien_from_Adresse_Lien(em, lien_txt2, message);
				
		    	System.out.println("Saisir le nouveau descriptif que vous voulez ajouter :");
		    	String new_desc=scan_boucle.nextLine(); 
		        LienDAO.Modify_Texte_Lien(em, message, lien2, new_desc);
		        System.out.println("Le descriptif du lien a �t� modifi�"); 
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
						System.out.println("Ce lien n'est pas utilis� dans votre message,"
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
			System.out.println("Souhaitez-vous modifier un autre lien ?"); 
			oui = scan_boucle.nextLine();
		}
	}
	
	//pas fait
	public static void Menu_Modify_Image(EntityManager em, Utilisateur utilisateur, Message message) {
		System.out.println("Souhaitez-vous modifier, supprimer ou ajouter une image � votre message ?");
		scanner.nextLine();
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
				System.out.println("Saisir le lien de l'image que vous voulez modifier :");
		    	String lien_image1=scanner.nextLine(); 
		    	boolean exist1 = false;
				do  {
					if(ImageDAO.GetImage_from_Adresse_serveur(em, lien_image1, message) == null) {
						exist1 = false;
					} else {
						exist1 = true;
					}
					if(exist1 == false) {
						System.out.println("Ce lien n'est pas utilis� dans votre message,"
								+ " veuillez en renseigner un nouveau :");
						lien_image1=scanner.nextLine(); 
					}
				}while(exist1 == false);
		    	
		    	Image image1 = ImageDAO.GetImage_from_Adresse_serveur(em, lien_image1, message);
		    	
		    	System.out.println("Saisir le nouveau lien de l'image :");
		    	String lien_new_image=scanner.nextLine(); 
		    	
		    	ImageDAO.Modify_Lien_serveur_Image(em, message, image1, lien_new_image );
				break;
			case 2:
				System.out.println("Saisir le lien de l'image que vous voulez supprimer :");
		    	String lien_image2=scanner.nextLine(); 
		    	boolean exist2 = false;
				do  {
					if(ImageDAO.GetImage_from_Adresse_serveur(em, lien_image2, message) == null) {
						exist2 = false;
					} else {
						exist2 = true;
					}
					if(exist2 == false) {
						System.out.println("Ce lien n'est pas utilis� dans votre message,"
								+ " veuillez en renseigner un nouveau :");
						lien_image2=scanner.nextLine(); 
					}
				}while(exist2 == false);
		    	
		    	Image image2 = ImageDAO.GetImage_from_Adresse_serveur(em, lien_image2, message);
		    	
		    	ImageDAO.Supprimer_Image(em, message, image2);
				break;
			case 3:
				Menu_Create_Image(em, utilisateur, message);
				break;
			default:
				System.out.println("Choix incorrect");
				break;
			}
			System.out.println("Souhaitez-vous modifier une autre image ?"); 
			oui = scanner.nextLine();
		}
	}
	
	public static void Menu_Modify_Mot_Cle(EntityManager em, Utilisateur utilisateur, Message message) {
		System.out.println("Souhaitez-vous modifier, supprimer ou ajouter un mot-cl� � votre message ?");
		scanner.nextLine();
		String oui = scanner.nextLine();
		while(oui.matches("oui")) {
			System.out.println("-------------------------------"); 
			System.out.println("1. Modifier un mot-cl�"); 
			System.out.println("2. Supprimer un mot-cl�"); 
			System.out.println("3. Ajouter un mot-cl�"); 
			System.out.println("-------------------------------"); 
			Scanner scan_boucle = new Scanner(System .in );
			int choix = scanner.nextInt();
			switch(choix) {
			case 1:
				System.out.println("Saisir le mot-cl� que vous voulez modifier :");
		    	String mot_cle_contenu=scan_boucle.nextLine(); 
		    	
		    	boolean exist = false;
				do  {
					if(Mot_CleDAO.GetMot_Cle_from_Contenu(em, mot_cle_contenu, message) == null) {
						exist = false;
					} else {
						exist = true;
					}
					if(exist == false) {
						System.out.println("Ce mot-cl� n'est pas utilis� dans votre message,"
								+ " veuillez en renseigner un nouveau :");
						mot_cle_contenu=scanner.nextLine(); 
					}
				}while(exist == false);
		    	
				Mot_Cle mot_cle = Mot_CleDAO.GetMot_Cle_from_Contenu(em, mot_cle_contenu, message);
				
		    	System.out.println("Saisir le nouveau mot-cl� que vous voulez ajouter :");
		    	String new_mot_cle=scan_boucle.nextLine(); 
		        Mot_CleDAO.Modify_Contenu_mot_cle(em, message, mot_cle, new_mot_cle);
		        System.out.println("Le mot-cl� a �t� modifi�"); 
				break;
			case 2:
				System.out.println("Saisir le mot-cl� que vous voulez supprimer :");
		    	String mot_cle_suppr=scan_boucle.nextLine(); 
		    	
		    	boolean exist2 = false;
				do  {
					if(Mot_CleDAO.GetMot_Cle_from_Contenu(em, mot_cle_suppr, message) == null) {
						exist2 = false;
					} else {
						exist2 = true;
					}
					if(exist2 == false) {
						System.out.println("Ce mot-cl� n'est pas utilis� dans votre message,"
								+ " veuillez en renseigner un nouveau :");
						mot_cle_suppr=scanner.nextLine(); 
					}
				}while(exist2 == false);
		    	
				Mot_Cle mot_cle2 = Mot_CleDAO.GetMot_Cle_from_Contenu(em, mot_cle_suppr, message);
				
		        Mot_CleDAO.Supprimer_Mot_Cle(em, message, mot_cle2);
		        System.out.println("Le mot-cl� a �t� supprim�");
				break;
			case 3:
				Menu.Menu_Create_Mot_Cle(em, utilisateur, message);
				break;
			default:
				System.out.println("Choix incorrect");
				break;
			}
			System.out.println("Souhaitez-vous modifier d'autre mot-cl� ?"); 
			oui = scan_boucle.nextLine();
		}
	}
	
	/**
	 * Methode permettant la gestion du sous menu d'affichage des messages
	 * @param em
	 * @param utilisateur
	 * @throws ParseException 
	 */
	public static void Menu_Affichage_Message(EntityManager em, Utilisateur utilisateur) throws ParseException {
		System.out.println("-------------------------------"); 
		System.out.println("1. Afficher tous les messages"); 
		System.out.println("2. Afficher tous vos messages"); 
		System.out.println("3. Afficher tous les messages par date"); 
		System.out.println("4. Afficher tous les messages par mot-cl�"); 
		System.out.println("-------------------------------"); 
		Scanner scan_boucle = new Scanner(System .in );
		int choix = scanner.nextInt();
		switch(choix) {
		case 1:
			List<Message> liste_all_message = MessageDAO.get_All_Message(em);
			Affichage_Message(em, liste_all_message);
			break;
		case 2:
			List<Message> liste_ses_message = MessageDAO.get_Message_Utilisateur(em, utilisateur);
			Affichage_Message(em, liste_ses_message);
			break;
		case 3:
			System.out.println("Saisir la date dont vous voulez voir les messages (format : AAAA-MM-JJ):");
	    	String date_message_txt=scan_boucle.nextLine(); 
	    	
			List<Message> liste_message_par_date = MessageDAO.get_Message_Par_Date(em, utilisateur, date_message_txt);
			Affichage_Message(em, liste_message_par_date);
			break;
		case 4:
			ArrayList<String> liste_mot_cle = new ArrayList<String>();
			System.out.println("Donnez le premier mot cl� :");
			scanner.nextLine();
			String mot1 = scanner.nextLine();
			List<Message> messages_corres = MessageDAO.get_Message_Par_Mot_Cle(em, utilisateur, mot1);
			List<Message> messages_corres_tmp = new ArrayList<Message>();
			List<Message> tmp = new ArrayList<Message>();
			liste_mot_cle.add(mot1);
			String mot_boucle;
			String oui = "";
			boolean autre = true;
			while(autre == true) {
				System.out.println("Avez-vous un mot cl� suppl�mentaire � donner ?");
				oui = scanner.nextLine();
				if (oui.matches("oui")) {
					System.out.println("Donnez un nouveau mot cl� :");
					mot_boucle = scanner.nextLine();
					liste_mot_cle.add(mot_boucle);
					tmp = MessageDAO.get_Message_Par_Mot_Cle(em, utilisateur, mot_boucle);
					for(int j = 0; j < tmp.size(); j++) {
						if(messages_corres.contains(tmp.get(j))) {
							messages_corres_tmp.add(tmp.get(j));
						}
					}
					messages_corres.clear();
					messages_corres.addAll(messages_corres_tmp);
				} else {
					autre = false;
				}
			}
			
			if(messages_corres.size() == 0) {
				System.out.println("Aucun message ne correspond � la liste des mots cl�s s�lectionn�s !");
				Menu_Affichage_Message(em, utilisateur);
			} else {
				Affichage_Message(em, messages_corres);
			}
			break;
		default:
			break;
		}
	}
	
	public static void Affichage_Message(EntityManager em, List<Message> liste_message) {
		for (Message message : liste_message){
			
			System.out.println("Titre : "+message.getTitre());
			System.out.println("Message : "+message.getTexte());
			System.out.println("Date de post : "+message.getDate_de_post());
			
			for(Lien lien : message.getListe_Lien()) {
				System.out.println("Lien : "+lien.getAdresse_Lien());
				System.out.println("Description : "+lien.getTexte());
			}
			
			for(Image image : message.getListe_Image()) {
				System.out.println("Lien de l'image : "+image.getLien_serveur());
			}
		}
	}
}
