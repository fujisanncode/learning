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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EntityScan(basePackages = {"ink.fujisann.learning.code.pojo"})
@EnableJpaRepositories(
        basePackages = {"ink.fujisann.learning.code.repository"},
        entityManagerFactoryRef = "learningFactoryBean",
        transactionManagerRef = "learningTransactionManger"
)
@EnableTransactionManagement
public class JpaLearningConfig {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(@Qualifier("learningDatasource") DataSource dataSource) {
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

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean learningFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .properties(hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings()))
                .packages("ink.fujisann.learning.code.pojo")
                .persistenceUnit("learningPersistenceUnit")
                .build();
    }

    @Primary
    @Bean
    public EntityManager learningEntityMange(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(learningFactoryBean(builder).getObject()).createEntityManager();
    }

    @Primary
    @Bean
    public JpaTransactionManager learningTransactionManger(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(learningFactoryBean(builder).getObject()));
    }
}
