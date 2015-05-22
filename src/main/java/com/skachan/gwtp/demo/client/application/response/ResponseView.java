package com.skachan.gwtp.demo.client.application.response;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import javax.inject.Inject;

public class ResponseView extends ViewWithUiHandlers<ResponseUiHandlers> implements ResponsePresenter.MyView {
    interface Binder extends UiBinder<Widget, ResponseView> {
    }

    @UiField
    HTML textToServer;
    @UiField
    HTML serverResponse;
    @UiField
    Button closeButton;

    @Inject
    ResponseView(
            Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setServerResponse(String serverResponse) {
        this.serverResponse.setHTML(serverResponse);
    }

    @Override
    public void setTextToServer(String textToServer) {
        this.textToServer.setHTML(textToServer);
    }

    @UiHandler("closeButton")
    void onClose(ClickEvent event) {
        getUiHandlers().onClose();
    }
}
