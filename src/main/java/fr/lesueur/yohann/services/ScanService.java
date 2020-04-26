package fr.lesueur.yohann.services;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface ScanService {
	public static final Logger logger = LogManager.getLogger(ScanService.class);
	
	Document	connect(String url);
	Elements	getMangaInfosHTML(Element doc, String...classNames);
	Elements	getMangaListHTML(Document doc, String...classNames);
	void		addMangaFromHTML(Elements anchors, MangaService mangaService);
	String		getImgUrl(String mangaUrl, int chapterInput);
	int			getTotalPage(Document doc);
	String		getCurrentImgUrl(ScanService scanService, int index, Document doc, String mangaUrl);
	void		resetUrls();
	
	default void downloadImg(String imgUrl, String downloadDirPath, int numPage) {
		Response resultImageResponse = null;
		try{				
			resultImageResponse = Jsoup.connect(imgUrl).ignoreContentType(true).execute();
			FileOutputStream out = (new FileOutputStream(new File(downloadDirPath)+"/page"+numPage));
			System.out.printf("Downloading: page %s%s", numPage, System.lineSeparator());
			out.write(resultImageResponse.bodyAsBytes());
			out.close();
		} catch (Exception e) {
			logger.error(e.getMessage() + " try to check: \n" + imgUrl + " is valid ?\n" + downloadDirPath + " exist ?\n");
			e.printStackTrace();
			System.exit(-1);
		}
	};
	
	default String createDownloadDir(String magaName, int chapterInput) {
		String currentDirectory = System.getProperty("user.dir");
		String nameInput = magaName.toLowerCase().replace(' ', '-');
		String fullName = currentDirectory+"/mangas/"+nameInput+"/chapter"+chapterInput;
		File newDir = new File(fullName);

		newDir.mkdirs();
		return newDir.getAbsolutePath();
	}
}
