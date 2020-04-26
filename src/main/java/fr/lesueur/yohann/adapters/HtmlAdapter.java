package fr.lesueur.yohann.adapters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.lesueur.yohann.entities.Manga;
import fr.lesueur.yohann.services.MangaService;
import fr.lesueur.yohann.services.ScanService;

/**
 * HtmlAdapter represent the raw HTML converter into a Manga 
 * 
 * @see Manga
 * 
 * */
public class HtmlAdapter {
	
	/**
	 * 
	 * @param elts
	 * @param elt
	 * @param scanService
	 * @param mangaService
	 * @return
	 */
	public static Manga toManga(Elements elts, Element elt, ScanService scanService, MangaService mangaService) {
		int lastChapter = 0;
		String lastChaperStr = scanService.getMangaInfosHTML(elt, "media-body").text();
		
        Pattern p = Pattern.compile("#\\d+");
        Matcher m = p.matcher(lastChaperStr);
        while(m.find()) {
            lastChapter = Integer.parseInt(m.group().substring(1));
        }
        
        Manga manga = null;
        if (!elts.text().isEmpty())
			manga = new Manga(elts.text(), elts.attr("href"), lastChapter);
		return manga;
	}
	public static Manga toManga(Element elt, ScanService scanService, MangaService mangaService) {
		int lastChapter = 0;
		String name = elt.getElementsByClass("hmi-titre").text();
		String lastChaperStr = elt.getElementsByClass("hmi-sub").text();
		lastChapter = Integer.parseInt(lastChaperStr.substring(19));
		
        Manga manga = null;
        if (!name.isEmpty())
			manga = new Manga(name, elt.attr("href"), lastChapter);
		return manga;
	}
}
