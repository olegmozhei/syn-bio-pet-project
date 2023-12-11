package my.service.database.configs;

import my.service.database.configs.IDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("default")
public class LocalDatasourceConfig implements IDataSource {
    private final DataSource dataSource;
    public LocalDatasourceConfig(){
        System.out.println("Trying to set up local datasource");
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("admin");
        this.dataSource=dataSource;
    }

    @Override
    @Bean
    public DataSource getDataSource() {
        return dataSource;
    }
}
