package com.skachan.gwtp.demo.server.guice;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;
import com.skachan.gwtp.demo.server.dispath.SendTextToServerHandler;
import com.skachan.gwtp.demo.shared.dispatch.SendTextToServerAction;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        bindHandler(SendTextToServerAction.class, SendTextToServerHandler.class);
    }
}
