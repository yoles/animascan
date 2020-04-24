package fr.lesueur.yohann.print.error;

public class PError {
	
	/**
	 * invalidArgs(): exit program with status code -1, when non-valid arguments are passed
	 */
	public static void invalidArgs() {
        System.out.println("Valid arguments are required  i.e: java -jar AnimaScan name chapter [source] | less");
        System.out.println(
        		"Enter: " + 
        		System.lineSeparator() + 
        		"\tjava -jar AnimaScan help "+ 
        		System.lineSeparator() +
        		"to see all available options "
		);
        System.exit(-1);
	}
	
	/**
	 * invalidChapter(): exit program with status code -1, when args [chapter] is not a number
	 */
	public static void invalidChapter() {
        System.out.println("Oops! That was no valid number.");
        invalidArgs();
	}
	
	
	public static void invalidMangaName(String nameInput) {
	    System.out.println("'" + nameInput + "' didn't exit, have you made a mistake in the name ?"+System.lineSeparator());
	    System.out.println("Here the list of manga's name available: ");
	}
}
