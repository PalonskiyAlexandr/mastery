package com.palonskiy.initializer;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@PropertySource("classpath:bd.properties")
@Component
public class DbInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${db.url}")
    private String url;
    @Value("${db.login}")
    private String login;
    @Value("${db.password}")
    private String password;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Flyway flyway = Flyway.configure().dataSource(url, login, password).locations("classpath:db/migration").load();
        flyway.migrate();
    }
}
