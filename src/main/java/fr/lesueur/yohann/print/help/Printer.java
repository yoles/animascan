package fr.lesueur.yohann.print.help;

import java.util.List;

import fr.lesueur.yohann.entities.Manga;

public class Printer {

	public static void mangasAvailable(List<Manga> mangas) {
	    for (Manga manga : mangas) {
	        System.out.println(manga.describeCLI());
	    }
	}
	
	public static String mangasAvailableGUI(List<Manga> mangas) {
		StringBuilder res = new StringBuilder("");
	    for (Manga manga : mangas) {
	    	res.append(manga.describeGUI());
	    }
	    return res.toString();
	}
	public static void help(List<Manga> mangas) {
		String msg = "PRESS Q TO EXIT THE HELPER MODE";
		StringBuilder wildCard = new StringBuilder("");
		for(int i = 0; i < msg.length(); i++) {
	        wildCard.append("*");
		}
	    System.out.printf("*****%s*****", wildCard.toString());
	    System.out.printf("*****%s*****", msg);
	    System.out.printf("*****%s*****", wildCard.toString());
	    //TODO 
	    System.out.println("NAME\n\tscan_dl - Command line to donwload scan chapter on https://www.scan-vf.net");
	    System.out.println("DESCRIPTION\n\tyou must write the manga's name and chapter as option of the program." +
	    		"You could set the web site source as 3th argument, scantrad is the default value");
	    System.out.println("SOURCE:\n\tscantrad\n\tscanvf");
	    System.out.println("EXAMPLE\n\tjava -jar AnimaScan  \"Golden Kamuy\" 158");
	    System.out.println("\tjava -jar AnimaScan  \"Golden Kamuy\" 158 scantrad\n");
	    System.out.println("MANGA AVAILABLE:");
	    mangasAvailable(mangas);
	}
	
	public static void CLIWelcome() {
		String msg = "Welcome to AnimaScan, scan donwloader ";
		StringBuilder wildCard = new StringBuilder("");
		for(int i = 0; i < msg.length(); i++) {
	        wildCard.append("*");
		}
	    System.out.printf("*****%s*****\n", wildCard.toString());
	    System.out.printf("*****%s*****\n", msg);
	    System.out.printf("*****%s*****\n", wildCard.toString());
	}
	
	public static String helpGUI(List<Manga> mangas) {
		String msg = "<html><body>AnimaScan est une interface pour télécharger des scan de manga<br/>via https://www.scantrad.net ou https://www.scan-vf.net<br/>" + 
				"Il faut remplir le nom du manga ainsi que le numero du chapitre.<br/>Si un des deux n'est pas disponible sur le site choisi, " +
				"vous ne pourez pas proceder au téléchargement<br/>" +
				"Si un manga n'est pas disponible sur la premiere source,<br/> essayez en selectionnant une autre source." + 
				"Voici la liste des manga disponibles: <br/><br/>"
		;
	    msg += mangasAvailableGUI(mangas) + "</body></html>";
	    return msg;
	}
}
