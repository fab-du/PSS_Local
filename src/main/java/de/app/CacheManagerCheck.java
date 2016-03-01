package de.app;

import java.util.Collection;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheManagerCheck implements CommandLineRunner{
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CacheManagerCheck.class);
	private CacheManager cacheManager;

	@Autowired
	public CacheManagerCheck(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("\n\n" + "=========================================================\n"
				+ "Using cache manager: " + this.cacheManager.getClass().getName() + "\n"
				+ "=========================================================\n\n");	
		
		Collection<String> cacheNames = cacheManager.getCacheNames(); 
		cacheNames.forEach( name -> {
			logger.info(name);
		});
	}
}
