package org.grape;

import com.google.common.base.Strings;
import io.ebean.EbeanServerFactory;
import io.ebean.Platform;
import io.ebean.config.DbMigrationConfig;
import io.ebean.config.PropertiesWrapper;
import io.ebean.config.ServerConfig;
import io.ebean.dbmigration.DbMigration;
import io.ebean.dbmigration.DbOffline;
import io.ebeaninternal.api.SpiEbeanServer;
import org.avaje.agentloader.AgentLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class GrapeDbMigration extends DbMigration {
    public GrapeDbMigration() {
        // set ebean auto enhance
        if (!AgentLoader.loadAgentFromClasspath("ebean-agent", "debug=1")) {
            logger.info("ebean-agent not found in classpath - not dynamically loaded");
        }

        super.addPlatform(Platform.H2, Platform.H2.name().toLowerCase());
        super.addPlatform(Platform.MYSQL, Platform.MYSQL.name().toLowerCase());
        super.addPlatform(Platform.ORACLE, Platform.ORACLE.name().toLowerCase());
        super.addPlatform(Platform.POSTGRES, Platform.POSTGRES.name().toLowerCase());
        super.addPlatform(Platform.SQLSERVER, Platform.SQLSERVER.name().toLowerCase());
        super.addPlatform(Platform.SQLITE, Platform.SQLITE.name().toLowerCase());
        super.addPlatform(Platform.DB2, Platform.DB2.name().toLowerCase());
    }


    public void generate(String plgName, String version) throws IOException {
        generate(plgName, version, null);
    }

    public void generate(String plgName, String version, String pendingDropVersion) throws IOException {
        DbOffline.setGenerateMigration();
        setPlatform(platforms.get(0).platform);

        this.serverConfig = new ServerConfig();
        this.serverConfig.addPackage(String.format("%s.domain", plgName));
        this.constraintNaming = serverConfig.getConstraintNaming();

        this.server = (SpiEbeanServer) EbeanServerFactory.create(serverConfig);

        migrationConfig = new DbMigrationConfig();
        Properties properties = new Properties();
        properties.setProperty("migration.name", plgName);
        properties.setProperty("migration.version", version);
        properties.setProperty("migration.migrationPath", String.format("sql/%s", plgName));
        if (!Strings.isNullOrEmpty(pendingDropVersion)) {
            properties.setProperty("migration.generatePendingDrop", pendingDropVersion);
        }
        migrationConfig.loadSettings(new PropertiesWrapper(properties), "db");

        super.generateMigration();
    }

    public void generate() throws IOException {
        System.setProperty("ebean.props.file", "sql_generator.properties");
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("sql_generator.properties")) {
            if (in == null) {
                throw new GrapeException("no config file sql_generator.properties.");
            }
        } catch (IOException e) {
            throw new GrapeException("no config file sql_generator.properties.", e);
        }
        super.generateMigration();
    }

}
