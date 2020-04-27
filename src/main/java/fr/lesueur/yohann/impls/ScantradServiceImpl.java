package fr.lesueur.yohann.impls;

import java.util.ArrayList;
import java.util.List;

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
 * @see ScantradServiceImpl#connect
 * @see ScantradServiceImpl#getMangaListHTML
 * @see ScantradServiceImpl#getTotalPage
 * @see ScantradServiceImpl#addMangaFromHTML
 * @see ScantradServiceImpl#getImgUrl
 * @see ScantradServiceImpl#getCurrentImgUrl
 * @see ScantradServiceImpl#resetUrls
 */
public class ScantradServiceImpl implements ScanService {

	/**
	 * Logger 
	 */
	private static final Logger logger = LogManager.getLogger(ScantradServiceImpl.class);
	
	/**
	 * List of images end part urls
	 */
	List<String> imgsUrl;
	
	/**
	 * Instance of scan which contains the web site source Url
	 */
	private Scan scan;

	/**
     * Constructor ScantradServiceImpl, initialize the Scan and Images URls list. 
     * The instance of Scan contain the web site source URL.
     * 
     * @param scan
     *            Instance of scan
     * 
     */
	public ScantradServiceImpl(Scan scan) {
		super();
		this.scan = scan;
		this.imgsUrl = new ArrayList<String>();
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
			logger.error("Error in url : " + e.getMessage() + " " + url);
			e.printStackTrace();
			System.exit(-1);
		}
		return doc;
	}

	/**
	 * get a Elements instance with links of the manga in
	 * @param  doc 
	 * 		Document instance, contains HTML tags
	 * @return instance of Elements, with <a></a> tags in
	 */
	@Override
	public Elements getMangaListHTML(Document doc, String...classNames) {
		String className = (classNames.length > 0) ? classNames[0] : "home-manga";
		Elements anchors = doc.body().getElementsByClass(className).select("a");
		return anchors;
	}
	
	@Override
	public Elements getMangaInfosHTML(Element doc, String...classNames) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * get all img html tag in Elements, and put src attribute text into {@code imgUrls} List.
	 * Finally return the size of list
	 * @param  doc 
	 * 		Document instance, contains HTML tags
	 * @return size of the list imgUrl
	 */
	@Override
	public int getTotalPage(Document doc) {
		Elements imgs = doc.select("img");
		for (Element img : imgs) {
			String imgUrl = img.attr("data-src");
			if(imgUrl.startsWith("lel/")) {
				this.imgsUrl.add(imgUrl);
			}
		}
		return imgsUrl.size();
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
			Manga manga = HtmlAdapter.toManga(a, this, mangaService);
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
		String url = scan.getUrl() + mangaUrl + "/"+chapterInput;
		return url;
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
		return "https://scantrad.net/" + this.imgsUrl.get(index-1);
	}
	
	/**
	 * reset the list of images url
	 * @param  scanService 
	 * 		not used @see ScanVfServiceImpl
	 * @param  Document doc 
	 * 		not used @see ScanVfServiceImpl
	 * @param String mangaUrl
	 * 		not used @see ScanVfServiceImpl
	 * 	@return url of the image
	 */
	@Override
	public void resetUrls() {
		this.imgsUrl = new ArrayList<>();
		
	}


}
