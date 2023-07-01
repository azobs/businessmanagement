package com.c2psi.businessmanagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing //To precise that we have some classes that spring must auditing.
/**
 * A class that spring must auditing is a class wich is annoted with @EntityListeners(AuditingEntityListener.class)
 * and use for some attribute:
 *  @CreatedDate
 *  @LastModifiedDate
 *  @CreatedBy
 *  @LastModifiedBy
 */
@Slf4j
public class BusinessmanagementApplication {

    @Value("${dir.images.articles}")
    public String photosArticleDir;

    @Value("${dir.images.persons}")
    public String photosPersonsDir;

    @PostConstruct
    void init(){
        try {
            log.info("Creation or verification folder for upload {}",photosPersonsDir);

            /*Path currentRelativePath = Paths.get("");
            String userDirectory = System.getProperty("user.dir");
            String s = currentRelativePath.toAbsolutePath().toString();
            log.info("Current absolute path is: {} et {}", s, userDirectory);*/

            Files.createDirectories(Paths.get(photosArticleDir));
            Files.createDirectories(Paths.get(photosPersonsDir));
            BMGlobalArguments.photosArticleDir = photosArticleDir;
            BMGlobalArguments.photosPersonsDir = photosPersonsDir;
            log.info("GlobalValues.photosPersonsDir  {}", photosPersonsDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(BusinessmanagementApplication.class, args);
    }
//http://localhost:8081/swagger-ui.html#/
}
