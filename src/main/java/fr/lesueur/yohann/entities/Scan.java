package fr.lesueur.yohann.entities;

/**
 * <b>Scan class representing the source where to find Manga</b>
 * @see Manga
 */
public class Scan {
	/**
	 * Url where to get all mangas availables
	 */
	private String url;

	/**
     * Constructeur Scan.
     * @param url
     *            web site source
     * 
     * @see Scan#url]
     */
	public Scan(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
