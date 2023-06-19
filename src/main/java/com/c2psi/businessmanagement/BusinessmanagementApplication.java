package com.c2psi.businessmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
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
public class BusinessmanagementApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(BusinessmanagementApplication.class, args);
    }
//http://localhost:8081/swagger-ui.html#/
}
