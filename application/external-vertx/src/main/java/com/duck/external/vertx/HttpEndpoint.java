package com.duck.external.vertx;

import io.vertx.core.http.HttpMethod;
import lombok.Value;

@Value(staticConstructor = "of")
public class HttpEndpoint {

    HttpMethod method;

    String path;
}