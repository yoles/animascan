package fr.lesueur.yohann.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PropertyService {

	private static final Logger logger = LogManager.getLogger(PropertyService.class);
	public static String ENV = "PROD";
    private Properties prop;
    private String currentSource;
	
	public PropertyService() {
		final String base = "source.properties";
		final String runner = (ENV.equals("DEV")) ? base : "resources/"+base;
		
		this.currentSource = "";
		try(InputStream input = PropertyService.class.getClassLoader().getResourceAsStream(runner)) {
            this.prop = new Properties();
            this.prop.load(input);
        } catch (IOException e) {
        	logger.error(e.getMessage());
        	e.printStackTrace();
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
