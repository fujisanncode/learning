package ink.fujisann.learning.base.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EntityScan(basePackages = {"ink.fujisann.learning.stock.pojo"})
@EnableJpaRepositories(
        basePackages = {"ink.fujisann.learning.stock.repository"},
        entityManagerFactoryRef = "stockFactoryBean",
        transactionManagerRef = "stockTransactionManger"
)
@EnableTransactionManagement
public class JpaStockConfig {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(@Qualifier("stockDatasource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private JpaProperties jpaProperties;

    @Autowired
    public void setJpaProperties(JpaProperties jpaProperties) {
        this.jpaProperties = jpaProperties;
    }

    private HibernateProperties hibernateProperties;

    @Autowired
    public void setHibernateProperties(HibernateProperties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean stockFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .properties(hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings()))
                .packages("ink.fujisann.learning.stock.pojo")
                .persistenceUnit("stockPersistenceUnit")
                .build();
    }

    @Bean
    public EntityManager stockEntityMange(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(stockFactoryBean(builder).getObject()).createEntityManager();
    }

    @Bean
    public JpaTransactionManager stockTransactionManger(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(stockFactoryBean(builder).getObject()));
    }
}
