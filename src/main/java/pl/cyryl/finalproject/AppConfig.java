package pl.cyryl.finalproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.cyryl.finalproject.util.FilesUtil;

@Configuration
public class AppConfig {
    @Bean
    public FilesUtil filesUtil(){
        return new FilesUtil();
    }
}
