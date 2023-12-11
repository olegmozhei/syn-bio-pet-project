package my.service.database.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("cloud")
public class CloudDatasourceConfig implements IDataSource {
    private final DataSource dataSource;

    public CloudDatasourceConfig(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUrl(System.getenv("DbUrl"));
        dataSource.setUsername(System.getenv("DbUsername"));
        dataSource.setPassword(System.getenv("DbPassword"));
        this.dataSource=dataSource;
    }


    @Override
    @Bean
    public DataSource getDataSource() {
        return dataSource;
    }
}
