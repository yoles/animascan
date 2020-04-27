package fr.lesueur.yohann.impls;

import java.util.ArrayList;
import java.util.List;

import fr.lesueur.yohann.entities.Manga;
import fr.lesueur.yohann.print.error.PError;
import fr.lesueur.yohann.print.help.Printer;
import fr.lesueur.yohann.services.MangaService;

/**
 * Implementation of the MangaService Interface. 
 * Provide different methods to manage an instance of Manga 
 * <ul>
 * <li>add a Manga in {@code List}</li>
 * <li>find a manga by is name</li>
 * <li>get a List of manga available</li>
 * <li>print a List of manga available</li>
 * <li>reset the manga List</li>
 * </ul>
 *
 */
public class MangaServiceImpl implements MangaService {


    /**
     * The list of manga availables
     * <p>
     * We can find a manga and add a manga to the list and reset or print the list.
     * <p>
     * 
     * @see MangaServiceImpl#printMangasAvailable()
     * @see MangaServiceImpl#add(Manga m)
     * @see MangaServiceImpl#reset()
     */
	private List<Manga> mangas;
	
	/**
     * Constructeur MangaServiceImpl, initialize the Manga list. 
     * The instance of mangaService contain all manga availables.
     * 
     * @param mangas
     *            list of mangas available
     * 
     * @see MangaServiceImpl#printMangasAvailable
     * @see MangaServiceImpl#add
     * @see MangaServiceImpl#reset
     * @see MangaServiceImpl#findByName
     */
	public MangaServiceImpl(List<Manga> mangas) {
		super();
		this.mangas = mangas;
	}


	/**
	 * Get all mangas
	 * @return the manga list
	 */
	@Override
	public List<Manga> getMangasAvailable() {
		return mangas;
	}

	/**
	 * find a manga by is name
	 * @return an instance of manga
	 */
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

	/**
	 * print in console all manga Availables
	 */
	@Override
	public void printMangasAvailable() {
		Printer.mangasAvailable(mangas);
	}


	/**
	 * Add a Manga to the list
	 * @param manga , instance of Manga
	 */
	@Override
	public void add(Manga manga) {
		if(manga != null) {
			mangas.add(manga);
		}
	}

	/**
	 * Reset the whole list
	 */
	@Override
	public void resetMangas() {
		this.mangas = new ArrayList<Manga>();
	}
	
}
