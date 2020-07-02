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
import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.naming.Named;
import org.quartz.Calendar;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@EachProperty(value = QuartzConfiguration.PREFIX + "." + QuartzClientConfiguration.PREFIX, primary = "default")
public class QuartzClientConfiguration implements Named {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzClientConfiguration.class);
    public static final String PREFIX = "clients";
    private static final String DEFAULT_CONFIG_FILE = "classpath:io.microanut.quartz.properties";

    private String configFile = DEFAULT_CONFIG_FILE;
    private ResourceResolver resourceResolver;
    private List<TriggerConfiguration> triggers = new ArrayList<>();
    private Map<String,Class<? extends Calendar>> calenders = new LinkedHashMap<>();
    private final String name;

    public QuartzClientConfiguration(@Parameter String name, ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
        this.name = name;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public List<TriggerConfiguration> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<TriggerConfiguration> triggers) {
        this.triggers = triggers;
    }

    public void setCalenders(Map<String, Class<? extends Calendar>> calenders) {
        this.calenders = calenders;
    }

    public Map<String, Class<? extends Calendar>> getCalenders() {
        return calenders;
    }

    public StdSchedulerFactory getBuilder() {
        StdSchedulerFactory builder = new StdSchedulerFactory();
        Optional<URL> configResource = resourceResolver.getResource(configFile);
        if (configResource.isPresent()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Reading io.microanut.quartz configuration from file: {}", configFile);
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

    @NonNull
    @Override
    public String getName() {
        return this.name;
    }

    @EachProperty(value = TriggerConfiguration.PREFIX, list = true)
    public static class TriggerConfiguration {
        public static final String PREFIX = "triggers";
        @ConfigurationBuilder(prefixes = {"set", "with"})
        private TriggerBuilder<Trigger> trigger = TriggerBuilder.newTrigger();
        @ConfigurationBuilder(value = "job", prefixes = {"set", "with"})
        private JobBuilder job = JobBuilder.newJob();

        public void setCron(String cron) {
            trigger.withSchedule(CronScheduleBuilder.cronSchedule(cron));
        }

        public TriggerBuilder<Trigger> getTrigger() {
            return trigger;
        }

        public JobBuilder getJob() {
            return job;
        }
    }
}
