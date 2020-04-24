package fr.lesueur.yohann.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyService {

    private Properties prop;
    private String currentSource;
    
	public PropertyService() {
		final String DEV = "source.properties";
		final String PROD = "resources/"+DEV;
		
		this.currentSource = "";
		try(InputStream input = PropertyService.class.getClassLoader().getResourceAsStream(PROD)) {
            this.prop = new Properties();
            prop.load(input);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
	}
	
	public String findByKey(String key) {
	   String source = prop.getProperty(key);
	   if (!source.isEmpty())
		   setCurrentSource(key);
       return source;
	}
	
	public void setCurrentSource(String source) {
		this.currentSource = source;
	}

	public String getCurrentSource() {
		return currentSource;
	}
	
}
