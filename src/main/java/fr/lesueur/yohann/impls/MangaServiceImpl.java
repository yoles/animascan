package fr.lesueur.yohann.impls;

import java.util.ArrayList;
import java.util.List;

import fr.lesueur.yohann.entities.Manga;
import fr.lesueur.yohann.print.error.PError;
import fr.lesueur.yohann.print.help.Printer;
import fr.lesueur.yohann.services.MangaService;

public class MangaServiceImpl implements MangaService {

	private List<Manga> mangas;
	
	
	public MangaServiceImpl(List<Manga> mangas) {
		super();
		this.mangas = mangas;
	}


	@Override
	public List<Manga> getMangasAvailable() {
		return mangas;
	}

	@Override
	public Manga findByName(String name) {
		name = name.trim().toLowerCase();
	    
		for(Manga manga : mangas) {
	    	if(name.equals(manga.getName().toLowerCase())) 
	    		return manga;	
	    }
		PError.invalidMangaName(name);
		printMangasAvailable();
	    return null;
	}

	@Override
	public void printMangasAvailable() {
		Printer.mangasAvailable(mangas);
	}


	@Override
	public void add(Manga manga) {
		if(manga != null) {
			mangas.add(manga);
		}
	}


	@Override
	public void resetMangas() {
		this.mangas = new ArrayList<Manga>();
	}
	
}
