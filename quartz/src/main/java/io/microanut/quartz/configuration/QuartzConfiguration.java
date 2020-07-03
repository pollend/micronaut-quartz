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

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.util.Toggleable;
import org.quartz.Calendar;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * quartz configuration.
 */
@ConfigurationProperties(value = QuartzConfiguration.PREFIX)
public class QuartzConfiguration implements Toggleable {
    public static final String PREFIX = "quartz";
    public static final String ENABLED = QuartzConfiguration.PREFIX + ".enabled";

    private Map<String, Class<? extends Calendar>> calenders = new LinkedHashMap<>();
    private boolean enabled = false;

    /**
     * @param enabled enable quartz scheduler.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @param calenders calendars for scheduler.
     */
    public void setCalenders(Map<String, Class<? extends Calendar>> calenders) {
        this.calenders = calenders;
    }

    /**
     * @return calenders calendars for scheduler.
     */
    public Map<String, Class<? extends Calendar>> getCalenders() {
        return calenders;
    }

    /**
     * @return enable quartz scheduler.
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
