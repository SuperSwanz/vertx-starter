package com.vertx.starter;

import io.vertx.core.Future;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.Router;
import rx.Single;

public class MainVerticle extends AbstractVerticle {

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
		    MainHandler handler = new MainHandler();
		    router.get("/:id").handler(handler::get);
		    router.get("/fetch").handler(handler::fetch);
			Single<HttpServer> server = createHttpServer(null, router, "localhost", 8080);
			server.subscribe(result -> {
				startFuture.complete();
				System.out.println("Vertx initialized");
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected Single<HttpServer> createHttpServer(HttpServerOptions httpOptions, Router router, String host, int port) {
		Single<HttpServer> server = vertx.createHttpServer()
										 .requestHandler(router::accept)
										 .rxListen(port, host);
		return server;
	}

}
