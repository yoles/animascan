package fr.lesueur.yohann.entities;

public class Manga {
	private String	name;
	private String	url;
	private int		lastChapter;
	
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

	public String describeGUI() {
		return "<br/>Nom: " + this.getName() + "<br/>Dernier Chapitre: " + this.getLastChapter() + "<br/>";
	}
	
	public String describeCLI() {
		return "\nNom: " + this.getName() + "\nDernier Chapitre: " + this.getLastChapter();
	}
	@Override
	public String toString() {
		return "Manga [name=" + name + ", url=" + url + ", lastChapter=" + lastChapter + "]";
	}
	
}
