package com.birdsnail.configserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.config.*;

/**
 * 实现{@link ConfigDataLocationResolver}，加载自定义的{@link ConfigDataResource}
 *
 * @author huadong.yang
 */
public class YhdConfigDataLocationResolver implements ConfigDataLocationResolver<YhdConfigDataResource> {

    public static final String PREFIX = "yhd";

    @Override
    public boolean isResolvable(ConfigDataLocationResolverContext context, ConfigDataLocation location) {
        return location.hasPrefix(PREFIX);
    }

    @Override
    public List<YhdConfigDataResource> resolve(ConfigDataLocationResolverContext context, ConfigDataLocation location)
            throws ConfigDataLocationNotFoundException, ConfigDataResourceNotFoundException {

        String configServerName = location.getValue();
        YhdConfigDataResource yhdConfigDataResource = new YhdConfigDataResource(configServerName);
        List<YhdConfigDataResource> resources = new ArrayList<>();
        resources.add(yhdConfigDataResource);
        return resources;
    }

}
