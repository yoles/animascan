package fr.lesueur.yohann.services;

import java.util.List;

import fr.lesueur.yohann.entities.Manga;

public interface MangaService {
	List<Manga> getMangasAvailable();
	void printMangasAvailable();
	Manga findByName(String name);
	void add(Manga manga);
	void resetMangas();
}
