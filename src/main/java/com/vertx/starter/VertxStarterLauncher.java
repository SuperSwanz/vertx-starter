package com.vertx.starter;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.launcher.VertxCommandLauncher;
import io.vertx.core.impl.launcher.VertxLifecycleHooks;
import io.vertx.core.json.JsonObject;

public class VertxStarterLauncher extends VertxCommandLauncher implements VertxLifecycleHooks {

	@Override
	public void afterConfigParsed(JsonObject config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeStartingVertx(VertxOptions options) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterStartingVertx(Vertx vertx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeDeployingVerticle(DeploymentOptions deploymentOptions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeStoppingVertx(Vertx vertx) {

	}

	@Override
	public void afterStoppingVertx() {

	}

	@Override
	public void handleDeployFailed(Vertx vertx, String mainVerticle, DeploymentOptions deploymentOptions,
			Throwable cause) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new VertxStarterLauncher().dispatch(args);
	}

	public static void executeCommand(String cmd, String... args) {
		new VertxStarterLauncher().execute(cmd, args);
	}
}