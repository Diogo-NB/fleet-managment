package com.diogo.nb.web2;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
public class FlywayConfig {

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .load();
    }

    @Bean
    public static BeanFactoryPostProcessor flywayDependencyPostProcessor() {
        return beanFactory -> {
            var bd = ((ConfigurableListableBeanFactory) beanFactory)
                    .getBeanDefinition("entityManagerFactory");
            String[] current = bd.getDependsOn();
            String[] updated;
            if (current == null) {
                updated = new String[]{"flyway"};
            } else {
                updated = Arrays.copyOf(current, current.length + 1);
                updated[current.length] = "flyway";
            }
            bd.setDependsOn(updated);
        };
    }
}
