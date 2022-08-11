package pl.cyryl.finalproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${app.user.item-images.location}")
    String dirName;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imagePathHandler(dirName))
                .addResourceLocations(fileLocation(dirName))
                .setCachePeriod(0);
    }

//    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
//        Path uploadDir = Paths.get(dirName);
//        String uploadPath = uploadDir.toFile().getAbsolutePath();
//
//        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
//
//        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
//    }

    private String imagePathHandler(String dirName){
        if (dirName.startsWith("../")){
            dirName = dirName.replace("../", "");
        }
        return "/" + dirName + "/**";
    }

    private String fileLocation(String dirName){
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        return "file:" + uploadPath + "/";
    }
}
