package com.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

public class MainVerticle extends AbstractVerticle {

	public static WebClient webclient;

	@Override
	public void start(Future<Void> startFuture) {
		try {
			super.start();
			Router router = Router.router(vertx);
		    router.route("/").handler(routingContext -> {
		        HttpServerResponse response = routingContext.response();
		        response
		            .putHeader("content-type", "application/json")
		            .end(new JsonObject().put("Welcome!", "Ok").toString());
		      });
		    WebClientOptions options = new WebClientOptions();
		    options.setConnectTimeout(20000);
		    options.setMaxPoolSize(10);
			options.setLogActivity(true);
			webclient = WebClient.create(vertx);
		    MainHandler handler = new MainHandler();
		    router.get("/get").handler(handler::get);
		    router.get("/fetch").handler(handler::fetch);
			router.get("/execute").handler(handler::execute);
			HttpServer server = createHttpServer(null, router, "localhost", 8080);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected HttpServer createHttpServer(HttpServerOptions httpOptions, Router router, String host, int port) {
		HttpServer server = vertx.createHttpServer()
										 .requestHandler(router::accept)
										 .listen(8080);
		return server;
	}

}
