package org.gzunzu.domain.loaders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class DataLoader {

    public DataLoader(final DataSource dataSource) {
        try {
            final ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                    new ClassPathResource("./data/data.sql")
            );
            populator.execute(dataSource);
            log.info("✅ Initial data successfully loaded from data.sql");
        } catch (Exception exception) {
            log.error("❌ Error loading initial data from data.sql: " + exception.getMessage(), exception);
        }
    }
}
