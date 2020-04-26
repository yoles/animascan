package fr.lesueur.yohann.print.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PError {

	private static final Logger logger = LogManager.getLogger(PError.class);
	/**
	 * invalidArgs(): exit program with status code -1, when non-valid arguments are passed
	 */
	public static void invalidArgs(String...args) {
        System.out.println("Valid arguments are required  i.e: java -jar AnimaScan name chapter [source] | less");
        System.out.println(
        		"Enter: " + 
        		System.lineSeparator() + 
        		"\tjava -jar AnimaScan help "+ 
        		System.lineSeparator() +
        		"to see all available options "
		);
		StringBuilder sb = new StringBuilder();
		for(String arg : args)
			sb.append("'" + arg + "' ");
        logger.error("Valid arguments are required: " + sb.toString() + "are not valid, need [String] [Number] [String]");
        System.exit(-1);
	}
	
	/**
	 * invalidChapter(): exit program with status code -1, when args [chapter] is not a number
	 */
	public static void invalidChapter(String...args) {
        System.out.println("Oops! That was no valid number.");
        logger.error(args[1] + " is not a number");
        invalidArgs(args);
	}
	
	
	public static void invalidMangaName(String nameInput) {
	    System.out.println("'" + nameInput + "' didn't exit, have you made a mistake in the name ?"+System.lineSeparator());
	    System.out.println("Here the list of manga's name available: ");
	}
}
