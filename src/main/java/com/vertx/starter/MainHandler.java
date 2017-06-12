package com.vertx.starter;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

public class MainHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext event) {

	}

	public void get(RoutingContext event) {
		final String data = event.request().getParam("id");
		event.response().end(new JsonObject().put("Get called with id: " + data, "Ok").toString());
	}

	public void fetch(RoutingContext event) {
		event.response().end(new JsonObject().put("Fetch called", "Ok").toString());
	}
}