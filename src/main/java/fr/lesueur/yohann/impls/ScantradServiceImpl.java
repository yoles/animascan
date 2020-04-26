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

public class ScantradServiceImpl implements ScanService {

	private static final Logger logger = LogManager.getLogger(ScantradServiceImpl.class);
	List<String> imgsUrl;
	private Scan scan;

	public ScantradServiceImpl(Scan scan) {
		super();
		this.scan = scan;
		this.imgsUrl = new ArrayList<String>();
	}

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

	@Override
	public void addMangaFromHTML(Elements anchors, MangaService mangaService) {
		for (Element a : anchors) {
			Manga manga = HtmlAdapter.toManga(a, this, mangaService);
			if (manga != null)
				mangaService.add(manga);
		}
	}

	@Override
	public String getImgUrl(String mangaUrl, int chapterInput) {
		String url = scan.getUrl() + mangaUrl + "/"+chapterInput;
		return url;
	}

	@Override
	public String getCurrentImgUrl(ScanService scanService, int index, Document doc, String mangaUrl) {
		return "https://scantrad.net/" + this.imgsUrl.get(index-1);
	}

	@Override
	public void resetUrls() {
		this.imgsUrl = new ArrayList<>();
		
	}


}
