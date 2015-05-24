package com.skachan.gwtp.demo.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author Stepan Kachan
 */

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
    }
}
