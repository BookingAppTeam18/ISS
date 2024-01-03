package rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.FileSystems;
import java.nio.file.Path;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        String relativePath = "BookinAppTeam18/src/main/resources/static/";

        // Koristimo Paths za kreiranje ispravne putanje
        Path absolutePath = FileSystems.getDefault().getPath(System.getProperty("user.dir"), relativePath);
        String absolutePathString = "file:///" + absolutePath.toString().replace("\\", "/")+"/";
        System.out.println("Absolute Path: " + absolutePathString);


        registry.addResourceHandler("api/content/**")
                .addResourceLocations(absolutePathString);
    }
}
