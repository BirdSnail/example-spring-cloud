package com.birdsnail.configserver;

import java.util.Objects;

import org.springframework.boot.context.config.ConfigDataResource;

public class YhdConfigDataResource extends ConfigDataResource {

    private String configServerName;

    public YhdConfigDataResource(String configServerName) {
        this.configServerName = configServerName;

    }

    public String getConfigServerName() {
        return configServerName;
    }

    @Override
    public String toString() {
        return "YhdConfigDataResource{" +
                "configServerName='" + configServerName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YhdConfigDataResource that = (YhdConfigDataResource) o;
        return configServerName.equals(that.configServerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configServerName);
    }
}
