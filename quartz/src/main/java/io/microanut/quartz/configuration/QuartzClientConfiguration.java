/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.microanut.quartz.configuration;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.naming.Named;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

/**
 * quartz configuration for individual schedulers.
 */
@EachProperty(value = QuartzConfiguration.PREFIX + "." + QuartzClientConfiguration.PREFIX, primary = "default")
public class QuartzClientConfiguration implements Named {
    public static final String PREFIX = "clients";

    private static final Logger LOG = LoggerFactory.getLogger(QuartzClientConfiguration.class);
    private static final String DEFAULT_CONFIG_FILE = "classpath:io.microanut.quartz.properties";

    private String configFile = DEFAULT_CONFIG_FILE;
    private final ResourceResolver resourceResolver;
    private final String name;

    public QuartzClientConfiguration(@Parameter String name, ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
        this.name = name;
    }

    /**
     * by default uses <code>classpath:io.microanut.quartz.properties</code>.
     *
     * @return the config file
     */
    public String getConfigFile() {
        return configFile;
    }

    /**
     * @param configFile the config file.
     */
    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    /**
     * @return create scheduler builder.
     */
    public StdSchedulerFactory getBuilder() {
        StdSchedulerFactory builder = new StdSchedulerFactory();
        Optional<URL> configResource = resourceResolver.getResource(configFile);
        Properties properties = new Properties();
        if (configResource.isPresent()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Reading io.microanut.quartz configuration from file: {}", configFile);
            }
            try (Reader r = new FileReader(Paths.get(configResource.get().toURI()).toFile())) {
                properties.load(r);
                builder.initialize(properties);
            } catch (SchedulerException | IOException | URISyntaxException e) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("An error has occurred while reading the configuration file [{}]: {}", configFile, e.getMessage());
                }
            }
        }
        return builder;
    }

    /**
     * @return name of scheduler
     */
    @NonNull
    @Override
    public String getName() {
        return this.name;
    }
}
