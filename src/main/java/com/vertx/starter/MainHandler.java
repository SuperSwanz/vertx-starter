package com.vertx.starter;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainHandler implements Handler<RoutingContext> {

    private WebClient webClient = MainVerticle.webclient;

    @Override
    public void handle(RoutingContext event) {

    }

    public void get(RoutingContext event) {
        final String data = event.request().getParam("id");
        final JsonObject response = new JsonObject().put("Get called with id: " + data, "Ok");
        event.response().putHeader("content-type", "application/json").putHeader("content-length", response.size() + "").end(response.toString());
        //event.response().putHeader("content-type", "application/json").end(response.toString());
    }

    public void fetch(RoutingContext event) {
        final JsonObject user = new JsonObject();
        user.put("id", 1);
        final JsonObject payload = new JsonObject().put("job", "job_" + user.getInteger("id")).put("name", "name_" + user.getLong("id"));
        MultiMap headers = MultiMap.caseInsensitiveMultiMap();
        headers.add("Content-Type", "application/json");
        trigger(HttpMethod.GET, "https://reqres.in/api/users/" + user.getLong("id"), payload, null).setHandler(handler -> {
            if (handler.succeeded()) {
                System.out.println(handler.result().toString());
                event.response().putHeader("content-type", "application/json").end(new JsonObject().put("Fetch called", "Ok").toString());
            } else {
                event.response().putHeader("content-type", "application/json").end(new JsonObject().put("Fetch failed", "Fail").toString());
            }
        });
    }

    public void execute(RoutingContext event) {
        List<Future> futures = new ArrayList<Future>();
        trigger(HttpMethod.GET, "https://reqres.in/api/users?page=2", null, null).setHandler(handler -> {
            if (!handler.succeeded()) {
                handler.cause().printStackTrace();
                event.response().putHeader("content-type", "application/json").end(new JsonObject().put("Execute failed: ", handler.cause().getMessage()).toString());
            } else {
                final JsonObject response = handler.result();
                final JsonArray users = response.getJsonArray("data");
                users.forEach(r -> {
                    final JsonObject user = ((JsonObject) r);
                    MultiMap headers = MultiMap.caseInsensitiveMultiMap();
                    headers.add("Content-Type", "application/json");
                    futures.add(trigger(HttpMethod.GET, "https://reqres.in/api/unknown/" + user.getLong("id"), null, headers));
                });

                CompositeFuture.all(futures).setHandler(ar -> {
                    if (ar.succeeded()) {
                        System.out.println("Success");
                        event.response().putHeader("content-type", "application/json").end(new JsonObject().put("Execute complete success", "Ok").toString());
                    } else {
                        ar.cause().printStackTrace();
                        event.response().putHeader("content-type", "application/json").end(new JsonObject().put("Execute complete failed: ", ar.cause().getMessage()).toString());
                    }
                });
            }
        });
    }

    public Future<JsonObject> trigger(final HttpMethod method, final String url, final JsonObject payload, final MultiMap headers) {
        Future<JsonObject> future = Future.future();
        HttpRequest<Buffer> request = webClient.requestAbs(method, url);
        Buffer buffer = Buffer.buffer();
        if (null != payload) {
            buffer = Buffer.buffer(payload.toString());
            request.headers().add(HttpHeaders.CONTENT_LENGTH.toString(), buffer.length() + "");
        }
        if (null != headers)
            request.headers().addAll(headers);

        request.send(ar -> {
            if (ar.succeeded()) {
                HttpResponse<Buffer> response = ar.result();
                System.out.println("Received response with status code: " + response.statusCode());
                future.complete(response.bodyAsJsonObject());
            } else {
                ar.cause().printStackTrace();
                future.fail(ar.cause());
            }
        });
        return future;
    }
}