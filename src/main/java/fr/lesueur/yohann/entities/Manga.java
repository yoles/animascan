package fr.lesueur.yohann.entities;

/**
 * <b>Manga class represent the Manga fetched as follow:</b>
 * <p>
 * <ul>
 * <li>Url of the website where to find images and chapters</li>
 * <li>Name of the manga as define on the web site source url</li>
 * <li>LastChapter available on site</li>
 * </ul>
 * </p>
 * <p>
 * These informations could change according to the selected source
 * </p>
 * 
 */
public class Manga {
	/**
	 * url to find informations about the manga
	 */
	private String	url;
	
	/**
	 * name of the manga
	 */
	private String	name;
	
	/**
	 * last chapter available
	 */
	private int		lastChapter;
	
	/**
     * Constructeur Manga.
     * 
     * @param url
     *            url of manga informations
     * @param name
     *            name of the manga
     * @param lastChapter
     *            last chapter avaiable
     * 
     * @see Manga#url
     * @see Manga#name
     * @see Manga#lastChapter
     */
	public Manga(String name, String url, int lastChapter) {
		super();
		this.name = name;
		this.url = url;
		this.lastChapter = lastChapter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public int getLastChapter() {
		return lastChapter;
	}

	public void setLastChapter(int lastChapter) {
		this.lastChapter = lastChapter;
	}

	/**
	 * Describe the manga for the Graphical User Interface
	 * @return name and last chapter available as String
	 */
	public String describeGUI() {
		return "<br/>Nom: " + this.getName() + "<br/>Dernier Chapitre: " + this.getLastChapter() + "<br/>";
	}
	
	/**
	 * Describe the manga for the Command Line Interface
	 * @return name and last chapter available as String
	 */
	public String describeCLI() {
		return "\nNom: " + this.getName() + "\nDernier Chapitre: " + this.getLastChapter();
	}
	
	@Override
	public String toString() {
		return "Manga [name=" + name + ", url=" + url + ", lastChapter=" + lastChapter + "]";
	}
	
}
