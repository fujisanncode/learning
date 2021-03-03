package ink.fujisann.learning.base.jpa;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.learning")
    public DataSource learningDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.stock")
    public DataSource stockDatasource() {
        return DataSourceBuilder.create().build();
    }

}
