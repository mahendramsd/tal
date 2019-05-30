package lk.dmg.talcacheredismanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/*.js/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/*.css/**").addResourceLocations("/resources/");
        //registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/");
    }
}
