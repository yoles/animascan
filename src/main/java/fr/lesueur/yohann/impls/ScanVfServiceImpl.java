package fr.lesueur.yohann.impls;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.lesueur.yohann.adapters.HtmlAdapter;
import fr.lesueur.yohann.entities.Manga;
import fr.lesueur.yohann.entities.Scan;
import fr.lesueur.yohann.services.MangaService;
import fr.lesueur.yohann.services.ScanService;

public class ScanVfServiceImpl implements ScanService{
	
	private Scan scan;

	public ScanVfServiceImpl(Scan scan) {
		super();
		this.scan = scan;
	}

	@Override
	public Document connect(String url) {
		Document doc = null;
		url = (url == null) ? scan.getUrl() : url;
		try {
			doc = Jsoup.connect(url).get();
		}catch (Exception e) {
			System.out.println("Error dans l'URL");
			e.printStackTrace();
			System.exit(-1);
		}
		return doc;
	}
	
	@Override
	public Elements getMangaListHTML(Document doc, String... classNames) {
		String className = (classNames.length > 0) ? classNames[0] : "media";
		Elements anchors = doc.body().getElementsByClass(className);
		return anchors;
	}
	
	@Override
	public Elements getMangaInfosHTML(Element elt, String...classNames) {
		String className = (classNames.length > 0) ? classNames[0] : "media-heading";
		return elt.getElementsByClass(className).select("a");
	}

	@Override
	public int getTotalPage(Document doc) {
		Elements options = doc.getElementsByClass("selectpicker").select("option");
		return options.size();
	}

	@Override
	public void addMangaFromHTML(Elements anchors, MangaService mangaService) {
		for (Element a : anchors) {
			Elements mangaInfosHTML = this.getMangaInfosHTML(a);
			Manga manga = HtmlAdapter.toManga(mangaInfosHTML, a, this, mangaService);
			if (manga != null)
				mangaService.add(manga);
		}
	}

	@Override
	public String getImgUrl(String mangaUrl, int chapterInput) {
		return mangaUrl + "/chapitre-" + chapterInput;
	}

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
