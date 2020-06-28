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
package io.microanut.quartz;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.io.ResourceResolver;
import org.quartz.SchedulerException;
import org.quartz.core.QuartzScheduler;
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

@ConfigurationProperties(QuartzConfiguration.PREFIX)
public class QuartzConfiguration {
    public static final String PREFIX = "quartz.client";
    private static final String DEFAULT_CONFIG_FILE = "classpath:quartz.properties";

    private static final Logger LOG = LoggerFactory.getLogger(QuartzConfiguration.class);

    private String configFile = DEFAULT_CONFIG_FILE;
    private ResourceResolver resourceResolver;

    public QuartzConfiguration(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public StdSchedulerFactory getBuilder() {
        StdSchedulerFactory builder = new StdSchedulerFactory();
        Optional<URL> configResource = resourceResolver.getResource(configFile);
        if(configResource.isPresent()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Reading quartz configuration from file: {}", configFile);
            }
            Properties properties = new Properties();
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
}
