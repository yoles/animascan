package fr.lesueur.yohann.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.lesueur.yohann.print.error.PError;
import fr.lesueur.yohann.print.help.Printer;
import fr.lesueur.yohann.services.MangaService;
import fr.lesueur.yohann.services.PropertyService;

public class ArgsValidator {

	private static final Logger logger = LogManager.getLogger(ArgsValidator.class);
	
	private final static int MAX_ARGS = 4;
	
	public static boolean checkGUIMod(String ...args) {
		for (String arg: args) {
			PropertyService.ENV = (arg.equals("DEV")) ? "DEV" : PropertyService.ENV; 
		}
	    return (args.length == 0 || args[0].toUpperCase().equals("DEV"));
	}
	
	public static void checkNumbers(String ...args) {
	    if (args.length > MAX_ARGS) {
	    	logger.error("Too much arguments");
	    	PError.invalidArgs();
	    }
	}
	
	public static void checkHelp(MangaService mangaService, String ...args) {
		if (args[0].equals("help") || args[0].equals("--help") || args[0].equals("-h")) {
			Printer.help(mangaService.getMangasAvailable());
			System.exit(0);
		}
	}
	
	public static String checkSource(PropertyService ps, String ...args) {
		String source = null;
		if (args.length > 2 && !(args[2].toUpperCase().equals("DEV"))) {
			String sourceArg = args[2].toLowerCase();
			source = ps.findByKey(sourceArg);
		}
		return (source == null) ? ps.findByKey("scantrad") : source;
	}
	
	public static String checkSourceGUI(PropertyService ps, String selected) {
		String source = null;
		
		if (!selected.isEmpty()) {
			String sourceArg = selected.toLowerCase();
			source = ps.findByKey(sourceArg);
		}
		return (source == null) ? ps.findByKey("scantrad") : source;
	}
}
