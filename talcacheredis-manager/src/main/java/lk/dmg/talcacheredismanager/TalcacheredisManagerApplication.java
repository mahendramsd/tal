package lk.dmg.talcacheredismanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackages = "lk.dmg")
@ComponentScan("lk.dmg")
public class TalcacheredisManagerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TalcacheredisManagerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TalcacheredisManagerApplication.class);
    }


}
