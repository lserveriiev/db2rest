package com.homihq.db2rest.jdbc.sql;

import com.homihq.db2rest.core.config.Db2RestConfigProperties;
import com.homihq.db2rest.jdbc.core.model.DbTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import java.sql.*;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
public class JdbcMetaDataProvider implements DatabaseMetaDataCallback<DbMeta> {

    private final Db2RestConfigProperties db2RestConfigProperties;

    List<MetaDataExtraction> exclusions = List.of(new OracleMetaDataExtraction(),
        new PostgreSQLDataExclusion(), new MySQLDataExtraction());

    //TODO include schemas , tables , view,  filters filters
    @Override
    public DbMeta processMetaData(DatabaseMetaData databaseMetaData) throws SQLException {

        log.info("Preparing database meta-data - {}", databaseMetaData);

        String productName = databaseMetaData.getDatabaseProductName();
        int majorVersion = databaseMetaData.getDatabaseMajorVersion();
        String productVersion = databaseMetaData.getDatabaseProductVersion();
        String driverName = databaseMetaData.getDriverName();
        String driverVersion = databaseMetaData.getDriverVersion();

        log.info("Product - {}", productName);
        log.info("Version - {}", productVersion);
        log.info("Major Version - {}", majorVersion);
        log.info("Driver Name - {}", driverName);
        log.info("Driver Version - {}", driverVersion);

        log.info("IncludedSchemas - {}", db2RestConfigProperties.getIncludeSchemas());
        log.info("All schema/catalog - {}", db2RestConfigProperties.isAllSchema());
        Optional<MetaDataExtraction> metaDataExclusion = getMetaDataExtraction(productName);

        if(metaDataExclusion.isEmpty()) throw new RuntimeException("Unable to extract metadata. No extractor");

        log.info("Fetching meta data for selected schemas.");

        List<DbTable> dbTables = metaDataExclusion.get().getTables(databaseMetaData,
                db2RestConfigProperties.isAllSchema(), db2RestConfigProperties.getIncludeSchemas());


        log.info("Completed loading database meta-data : {} tables", dbTables.size());

        return new DbMeta(productName, majorVersion, driverName, driverVersion, dbTables);
    }



    private Optional<MetaDataExtraction> getMetaDataExtraction(String productName) {
        return this.exclusions.stream().filter(
                metaDataExtraction -> metaDataExtraction.canHandle(productName)
        ).findFirst();
    }


}
