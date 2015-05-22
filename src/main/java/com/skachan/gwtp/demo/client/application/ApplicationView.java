package com.skachan.gwtp.demo.client.application;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import javax.inject.Inject;

public class ApplicationView extends ViewWithUiHandlers<ApplicationUiHandlers> implements ApplicationPresenter.MyView {
    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    TextBox nameField;
    @UiField
    Button sendButton;
    @UiField
    HTML error;

    @Inject
    ApplicationView(
            Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void resetAndFocus() {
        nameField.setFocus(true);
        nameField.selectAll();
    }

    @Override
    public void setError(String errorText) {
        error.setHTML(errorText);
    }

    @UiHandler("sendButton")
    void onSend(ClickEvent event) {
        getUiHandlers().sendName(nameField.getText());
    }
}
