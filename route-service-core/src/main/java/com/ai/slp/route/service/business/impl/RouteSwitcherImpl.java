package com.ai.slp.route.service.business.impl;

import com.ai.slp.route.core.PriorityRoutesMapping;
import com.ai.slp.route.core.Route;
import com.ai.slp.route.core.RouteGroup;
import com.ai.slp.route.service.business.interfaces.IRouteSwitcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RouteSwitcherImpl implements IRouteSwitcher {

    private Logger logger = LogManager.getLogger(RouteSwitcherImpl.class);

    @Override
    public Route switchRoute(String tenantId, String groupId, String dataJson) {
        RouteGroup routeGroup = RouteGroup.load(tenantId, groupId);
        if (routeGroup == null) {
            logger.error("Failed to find the tenantId:{}, groupId:{}", tenantId, groupId);
            return null;
        }
        return switchRoute(routeGroup, dataJson);
    }

    private Route switchRoute(RouteGroup routeGroup, String dataJson) {
        Route route = null;
        for (PriorityRoutesMapping priorityRoutesMapping : routeGroup.getPriorityRouteMapping()) {
            route = priorityRoutesMapping.switchRoute(dataJson);
            if (route != null) {
                break;
            }
        }
        return route;
    }
}
