package pl.cyryl.finalproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.cyryl.finalproject.util.FilesService;

@Configuration
public class AppConfig {
    @Bean
    public FilesService filesUtil(){
        return new FilesService();
    }
}
