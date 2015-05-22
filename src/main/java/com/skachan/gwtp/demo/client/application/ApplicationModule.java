package com.skachan.gwtp.demo.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.skachan.gwtp.demo.client.application.response.ResponseModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new ResponseModule());

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
    }
}
