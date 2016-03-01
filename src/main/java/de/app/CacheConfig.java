package de.app;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {
	
	public final static String CACHE_USERS = "cacheUsers";
	public final static String CACHE_DOCUMENTS = "cacheDocuments";
	public final static String CACHE_GROUPS = "cacheGroups";
	public final static String CACHE_FRIENDS = "cacheFriends";
	public final static String CACHE_SESSION = "cacheSession";

	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CacheConfig.class);

	@Bean
	@Override
	public CacheManager cacheManager() {
		logger.info("initializing simple Guava Cache Manager");
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		
		GuavaCache cache1 = new GuavaCache(CACHE_USERS, CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build());
		GuavaCache cache2 = new GuavaCache(CACHE_DOCUMENTS, CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build());
		GuavaCache cache3 = new GuavaCache(CACHE_GROUPS, CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build());
		GuavaCache cache4 = new GuavaCache(CACHE_FRIENDS, CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build());
		GuavaCache cache5 = new GuavaCache(CACHE_SESSION, CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build());

		cacheManager.setCaches( Arrays.asList( cache1, cache2 , cache3, cache4, cache5));

		return cacheManager;
	}

	@Override
	public CacheResolver cacheResolver() {
		return null;
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

	@Override
	public CacheErrorHandler errorHandler() {
		return new SimpleCacheErrorHandler();
	}

}
