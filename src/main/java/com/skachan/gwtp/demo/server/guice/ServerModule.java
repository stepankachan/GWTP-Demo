package com.skachan.gwtp.demo.server.guice;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;
import com.skachan.gwtp.demo.server.dispath.GetUsersHandler;
import com.skachan.gwtp.demo.shared.action.GetUsersAction;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        bindHandler(GetUsersAction.class, GetUsersHandler.class);
    }
}
