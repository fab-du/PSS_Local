package de.app;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ServeStaticConfig  extends WebMvcConfigurerAdapter{
    
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("downloads/save/**")
          .addResourceLocations("file:" + this.currentJarDir() + "/**");
    }
    
     String currentJarDir(){
    	File f = new File(System.getProperty("java.class.path"));
    	File dir = f.getAbsoluteFile().getParentFile();
    	String path = dir.toString();
    	return path;
    }
}
