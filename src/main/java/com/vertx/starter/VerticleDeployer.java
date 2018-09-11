package com.vertx.starter;

import io.vertx.core.Vertx;

public class VerticleDeployer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle(), ar -> {
            System.out.println(ar.cause());
            System.out.println(ar.result());
            if (ar.succeeded()) {
                System.out.println(String.join(" ", "MainVerticle deployed successfully with deployment id: ", ar.result()));
            } else {
                System.out.println(String.join(" ", "Failed to deploy MainVerticle"));
            }
        });
    }
}
