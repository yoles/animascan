package fr.lesueur.yohann.validator;

import fr.lesueur.yohann.print.error.PError;
import fr.lesueur.yohann.print.help.Printer;
import fr.lesueur.yohann.services.MangaService;
import fr.lesueur.yohann.services.PropertyService;

public class ArgsValidator {
	private final static int MAX_ARGS = 3;
	
	public static void checkNumbers(String ...args) {
	    if (args.length > MAX_ARGS) {
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
		
		if (args.length >= 2) {
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
