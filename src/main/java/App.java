
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.lesueur.yohann.entities.Manga;
import fr.lesueur.yohann.entities.Scan;
import fr.lesueur.yohann.gui.Window;
import fr.lesueur.yohann.impls.MangaServiceImpl;
import fr.lesueur.yohann.impls.ScanVfServiceImpl;
import fr.lesueur.yohann.impls.ScantradServiceImpl;
import fr.lesueur.yohann.print.error.PError;
import fr.lesueur.yohann.print.help.Printer;
import fr.lesueur.yohann.services.MangaService;
import fr.lesueur.yohann.services.PropertyService;
import fr.lesueur.yohann.services.ScanService;
import fr.lesueur.yohann.validator.ArgsValidator;
public class App {
	private static final Logger logger = LogManager.getLogger(App.class);
	
	public static void main(String[] args) {
		
		if(args.length == 0) {
			Window.main(args);
		} else {
			ArgsValidator.checkNumbers(args);
			
			MangaService mangaService = new MangaServiceImpl(new ArrayList<Manga>());
			ScanService scanService ;
			
			PropertyService ps = new PropertyService();
			String sourceUrl = ArgsValidator.checkSource(ps, args);
			Scan scan = new Scan(sourceUrl);
			
			switch (ps.getCurrentSource()) {
			case "scantrad":
				scanService = new ScantradServiceImpl(scan);
				break;
			case "scanvf":
				scanService = new ScanVfServiceImpl(scan);
				break;	
			default:
				scanService = new ScanVfServiceImpl(scan);
				break;
			}
			
			Document doc = scanService.connect(null);
			Elements anchors = scanService.getMangaListHTML(doc);
			scanService.addMangaFromHTML(anchors, mangaService);

			ArgsValidator.checkHelp(mangaService, args);
			Printer.CLIWelcome();
			
			String nameInput = args[0];
			int chapterInput = 0;
			
			Manga manga = mangaService.findByName(nameInput);
			if (manga == null) {
		        logger.error(nameInput + " is not on web site source: " + sourceUrl + ", try to change the source");
				System.exit(-1);
			}
			try {
				chapterInput = (args[1].equals("last")) ? manga.getLastChapter() : Integer.parseInt(args[1]);
			} catch (Exception e) {
				PError.invalidChapter(args);
			}
			
			String url = scanService.getImgUrl(manga.getUrl(), chapterInput);
			doc = scanService.connect(url);
			
			int totalPage = scanService.getTotalPage(doc);
			String downloadDirPath = scanService.createDownloadDir(manga.getName(), chapterInput);

			for(int i = 1; i <= totalPage; i++) {
				String imgUrl = scanService.getCurrentImgUrl(scanService, i, doc, url);	
				scanService.downloadImg(imgUrl, downloadDirPath, i);
			}
		}
	}
}
