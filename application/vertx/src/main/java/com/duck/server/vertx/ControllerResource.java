package com.duck.server.vertx;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public interface ControllerResource extends Handler<RoutingContext> {
    List<HttpEndpoint> endpoints();
}
