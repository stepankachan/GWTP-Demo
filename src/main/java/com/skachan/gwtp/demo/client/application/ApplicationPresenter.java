package com.skachan.gwtp.demo.client.application;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.skachan.gwtp.demo.client.place.NameTokens;
import com.skachan.gwtp.demo.server.model.User;
import com.skachan.gwtp.demo.shared.action.GetUsersAction;
import com.skachan.gwtp.demo.shared.action.GetUsersResult;

import javax.inject.Inject;
import java.util.List;

public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy>
        implements ApplicationUiHandlers {

    @ProxyStandard
    @NameToken(NameTokens.home)
    interface MyProxy extends ProxyPlace<ApplicationPresenter> {
    }

    interface MyView extends View, HasUiHandlers<ApplicationUiHandlers> {
        void populateTable(List<User> list);
    }

    private final DispatchAsync dispatcher;

    @Inject
    ApplicationPresenter(EventBus eventBus, MyView view, MyProxy proxy, PlaceManager placeManager,DispatchAsync dispatcher) {
        super(eventBus, view, proxy, RevealType.Root);
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

    @Override
    public void getUsers() {

    }

    @Override
    protected void onReset() {
        super.onReset();
        dispatcher.execute(new GetUsersAction(),new AsyncCallback<GetUsersResult>(){

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("get users error");
        }
            @Override
            public void onSuccess(GetUsersResult result) {
            getView().populateTable(result.getUserList());
            }
        });
    }

}
