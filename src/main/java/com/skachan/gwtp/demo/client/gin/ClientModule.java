package com.skachan.gwtp.demo.client.gin;

import com.gwtplatform.dispatch.rpc.client.gin.RpcDispatchAsyncModule;
import com.gwtplatform.mvp.client.annotations.GaAccount;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.skachan.gwtp.demo.client.application.ApplicationModule;
import com.skachan.gwtp.demo.client.place.NameTokens;

/**
 * @author Stepan Kachan
 */

public class ClientModule extends AbstractPresenterModule {

    private static final String ANALYTICS_ACCOUNT = "UA-8319339-6";

    @Override
    protected void configure() {
        install(new DefaultModule.Builder()
                .defaultPlace(NameTokens.home)
                .errorPlace(NameTokens.home)
                .unauthorizedPlace(NameTokens.home)
                .build());
        install(new RpcDispatchAsyncModule());
        install(new ApplicationModule());

        bindConstant().annotatedWith(GaAccount.class).to(ANALYTICS_ACCOUNT);

    }
}
