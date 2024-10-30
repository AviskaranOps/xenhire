package xenhire.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                			"http://localhost:3000", 
                			"http://localhost:3001", 
                			"https://xenflexer.com", 
                			"http://localhost:8080",
                			"https://xenrecruit.com", 
                			"http://xenrecruit.com",
                			"http://xentalenthub.com",
                			"https://xentalenthub.com"
                		) 
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("Content-Disposition") // Expose Content-Disposition
                .allowCredentials(true);
    }
}
