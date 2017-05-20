package org.grape;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import org.avaje.agentloader.AgentLoader;
import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Lewis
 * 2017-05-20.
 */
@SpringBootApplication
@PropertySource("classpath:application.properties")
public class GrapeApp {
    private static final Logger logger = LoggerFactory.getLogger(GrapeApp.class);

    private static final Properties properties = new Properties();
    private static ServerConfig serverConfig;

    static {
        // loadProperties
        try (InputStream is = GrapeApp.class.getResourceAsStream("/application.properties")) {
            properties.load(is);
        } catch (IOException e) {
            throw new GrapeException("load application.properties error.", e);
        }

        // setEbeanAutoEnhance
        AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1");
    }


    public static void main(String[] args) {
        List<GrapePlugin> plugins = loadPlugin();
        plugins.forEach(GrapePlugin::inTheBeginning);

        createEbeanServer();
        flyway(plugins);
        plugins.forEach(GrapePlugin::afterDataBaseInitial);

        final ConfigurableApplicationContext appContext = initSpring(plugins, args);
        plugins.forEach(plg -> plg.afterSpringInitial(appContext));

        // start success
        logger.info("\r\n*******************************************\r\n" //
                + "#######		STARTUP SUCCESS\r\n" //
                + "#######		http://localhost:" + getConfig("server.port") + "\r\n"//
                + "*******************************************\r\n");


        plugins.forEach(plg -> plg.afterStarted(appContext));
    }

    private static ConfigurableApplicationContext initSpring(List<GrapePlugin> plugins, String[] args) {
        logger.info("Init spring begin.");
        ArrayList<Object> ss = Lists.newArrayList(GrapeApp.class);
        ss.addAll(plugins.stream().map(GrapePlugin::name).map(Package::getPackage).collect(Collectors.toList()));
        SpringApplication app = new SpringApplication(ss.toArray());

        // init spring
        logger.info("Loading spring beans, this will take some time, please be patient.");
        final ConfigurableApplicationContext appContext = app.run(args);
        logger.info("Init spring end.\n");
        return appContext;
    }

    private static void createEbeanServer() {
        serverConfig = new ServerConfig();
        serverConfig.setName("default");
        serverConfig.setDefaultServer(true);
        serverConfig.loadFromProperties(GrapeApp.properties);
        serverConfig.setRegister(true);
        serverConfig.setLazyLoadBatchSize(100);

        EbeanServer ebeanServer = EbeanServerFactory.create(serverConfig);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ebeanServer.shutdown(true, true);
            }
        });
    }

    private static void flyway(List<GrapePlugin> plugins) {
        logger.info("Database migration begin.");
        for (GrapePlugin plg : plugins) {
            if (plg.hasEntity()) {
                Flyway flyway = new Flyway();
                flyway.setLocations(String.format("%s/dbmigration/", plg.name()));
                flyway.setTable(plg.name() + "_schema_versions");
                flyway.setDataSource(serverConfig.getDataSource());
                flyway.setValidateOnMigrate(false);
                flyway.setEncoding("UTF-8");
                flyway.setSqlMigrationPrefix("");
                flyway.setSqlMigrationSeparator("__");
                flyway.setSqlMigrationSuffix(String.format("-%s-.sql", serverConfig.getDatabasePlatform()
                        .getName()));
                flyway.migrate();
                logger.info(String.format("Migration plugin %s.", plg.name()));
            } else {
                logger.info(String.format("Ignore migration: %s", plg.name()));
            }
        }
    }


    private static List<GrapePlugin> loadPlugin() {
        logger.info("Load pitaya plugin begin.");

        Set<GrapePlugin> set = Sets.newHashSet();

        ServiceLoader<GrapePlugin> plugins = ServiceLoader.load(GrapePlugin.class);
        for (GrapePlugin plugin : plugins) {
            logger.info("find pitaya plugin: " + plugin.name());
            if (set.contains(plugin)) {
                String msg = String.format("Duplicate plugin: %s, plugin name must be unique.", plugin.name());
                logger.warn(msg);
                throw new GrapeException(msg);
            }
            set.add(plugin);
        }

        List<GrapePlugin> list = Lists.newArrayList(set);
        Collections.sort(list);
        List<String> on = list.stream().map(GrapePlugin::name).collect(Collectors.toList());
        logger.info("Plugin order: " + String.join(", ", on));
        logger.info("Load pitaya plugin end.\n");
        return list;
    }

    /**
     * Read config in application.properties
     *
     * @param name the property namen
     * @return the property value
     */
    @NotNull
    public static String getConfig(String name) {
        String value = properties.getProperty(name);
        if (Strings.isNullOrEmpty(value)) {
            String msg = String.format("%s must config in application.properties.", name);
            logger.error("\r\n********************************************************************\r\n" //
                    + "#######		Missing Config  \r\n" //
                    + "#######		" + msg + "\r\n"//
                    + "********************************************************************\r\n");
            throw new GrapeException(msg);
        }
        return value;
    }

    public static String getProperty(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    public static Properties getProperties() {
        return properties;
    }


}
