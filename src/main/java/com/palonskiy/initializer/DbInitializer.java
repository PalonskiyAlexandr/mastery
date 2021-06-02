package com.palonskiy.initializer;

import com.palonskiy.dao.CrudDaoImpl;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Properties;

@PropertySource("classpath:bd.properties")
@Component
public class DbInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${db.url}")
    private String url;
    @Value("${db.login}")
    private String login;
    @Value("${db.password}")
    private String password;

    private static final Logger logger = LoggerFactory.getLogger(CrudDaoImpl.class);

    @Override
    //TODO refactor migrate code
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("flyway migration have started");
        Flyway flyway = new Flyway();
        flyway.setDataSource(url, login, password);
        flyway.setLocations("classpath:db/migration");
        flyway.migrate();
    }
}
