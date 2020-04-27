package fr.lesueur.yohann.impls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.lesueur.yohann.adapters.HtmlAdapter;
import fr.lesueur.yohann.entities.Manga;
import fr.lesueur.yohann.entities.Scan;
import fr.lesueur.yohann.services.MangaService;
import fr.lesueur.yohann.services.ScanService;

/**
 * Implementation of the ScantradService Interface. 
 * Provide different methods to connect, fetch and manage data with Jsoup Api
 * <ul>
 * <li>Connextion on specified URL</li>
 * <li>Get a list of manga, in HTML tag format</li>
 * <li>Get Number of page available</li>
 * <li>Convert a html fetch manga into a Manga instance </li>
 * <li>Get the base of images url</li>
 * <li>Get image on current page</li>
 * <li>Reset the list of url images</li>
 * </ul>
 *
 * @see ScanVfServiceImpl#connect
 * @see ScanVfServiceImpl#getMangaListHTML
 * @see ScanVfServiceImpl#getTotalPage
 * @see ScanVfServiceImpl#addMangaFromHTML
 * @see ScanVfServiceImpl#getImgUrl
 * @see ScanVfServiceImpl#getCurrentImgUrl
 * @see ScanVfServiceImpl#resetUrls
 */
public class ScanVfServiceImpl implements ScanService{
	
	/**
	 * Logger 
	 */
	private static final Logger logger = LogManager.getLogger(ScanVfServiceImpl.class);
	
	/**
	 * Instance of scan which contains the web site source Url
	 */
	private Scan scan;

	/**
     * Constructor ScanVfServiceImpl, initialize the Scan. 
     * The instance of Scan contain the web site source URL.
     * 
     * @param scan
     *            Instance of scan
     * 
     */
	public ScanVfServiceImpl(Scan scan) {
		super();
		this.scan = scan;
	}
	
	/**
	 * Try to connect to URl, if something go wrong with url, an exception is raised and write in log file
	 * @param  url 
	 * 		url to connect
	 * @return an instance of Document, within HTML tag page
	 */
	@Override
	public Document connect(String url) {
		Document doc = null;
		url = (url == null) ? scan.getUrl() : url;
		try {
			doc = Jsoup.connect(url).get();
		}catch (Exception e) {
			logger.error("Error in url : " + e.getMessage() + " "  + url);
			e.printStackTrace();
			System.exit(-1);
		}
		return doc;
	}
	

	/**
	 * get a Elements instance with links of the manga and other informations in
	 * @param  doc 
	 * 		Document instance, contains HTML tags
	 * @param  classNames 
	 * 		String array with class Name, if we dont want the default one
	 * @return instance of Elements, with <a></a> tags in
	 */
	@Override
	public Elements getMangaListHTML(Document doc, String... classNames) {
		String className = (classNames.length > 0) ? classNames[0] : "media";
		Elements anchors = doc.body().getElementsByClass(className);
		return anchors;
	}
	

	/**
	 * get a info about the Manga from HTML tag
	 * @param  elt 
	 * 		Element instance, contains HTML tags
	 * @param  classNames 
	 * 		String array with class Name, if we dont want the default one
	 * @return instance of Elements, with <a></a> tags in
	 * @see HTMLadapter#toManga
	 */
	@Override
	public Elements getMangaInfosHTML(Element elt, String...classNames) {
		String className = (classNames.length > 0) ? classNames[0] : "media-heading";
		return elt.getElementsByClass(className).select("a");
	}

	/**
	 * get all options html tag in Document, and return the size of it
	 * @param  doc 
	 * 		Document instance, contains HTML tags
	 * @return size of the list imgUrl
	 */
	@Override
	public int getTotalPage(Document doc) {
		Elements options = doc.getElementsByClass("selectpicker").select("option");
		return options.size();
	}

	/**
	 * for each links in Elements anchors, convert the HTMl tag into a Manga instance 
	 * before adding it into mangaService List 
	 * and return the size of list
	 * @param  doc 
	 * 		Document instance, contains HTML tags
	 * @return size of the imgUrl {@code List}
	 */
	@Override
	public void addMangaFromHTML(Elements anchors, MangaService mangaService) {
		for (Element a : anchors) {
			Elements mangaInfosHTML = this.getMangaInfosHTML(a);
			Manga manga = HtmlAdapter.toManga(mangaInfosHTML, a, this, mangaService);
			if (manga != null)
				mangaService.add(manga);
		}
	}

	/**
	 * @param  mangaUrl 
	 * 		part of url contains the manga path name
	 * @param  chapterInput 
	 * 		chapter number to download
	 * @return full url to get the first Image
	 */
	@Override
	public String getImgUrl(String mangaUrl, int chapterInput) {
		return mangaUrl + "/chapitre-" + chapterInput;
	}
	
	/**
	 * get current image url to download on page 
	 * @param  scanService 
	 * 		not used @see ScanVfServiceImpl
	 * @param  Document doc 
	 * 		not used @see ScanVfServiceImpl
	 * @param String mangaUrl
	 * 		not used @see ScanVfServiceImpl
	 * 	@return url of the image
	 */
	@Override
	public String getCurrentImgUrl(ScanService scanService, int index, Document doc, String mangaUrl) {
		String url = mangaUrl + "/" + index;
		doc = this.connect(url);
		String imgUrl = doc.getElementsByClass("img-responsive scan-page").attr("src");
		return imgUrl;
	}

	@Override
	public void resetUrls() {
		// TODO Auto-generated method stub
		
	}
	
}
