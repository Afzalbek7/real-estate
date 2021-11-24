package maroqand.uz.real_estate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("indesx");
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path adUploadDir = Paths.get("./ad-images");
        String adUploadPath = adUploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/ad-images/**").addResourceLocations("file:/" +
                adUploadPath + "/");
    }
}
