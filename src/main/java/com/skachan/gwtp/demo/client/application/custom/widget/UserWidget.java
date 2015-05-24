package com.skachan.gwtp.demo.client.application.custom.widget;

import com.github.gwtbootstrap.client.ui.Heading;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.skachan.gwtp.demo.server.model.User;

/**
 * @author Stepan Kachan
 */
public class UserWidget extends Composite {

    interface UsersWidgetUiBinder extends UiBinder<Widget, UserWidget> {
    }

    @UiField
    Heading hUserTitle,pName,pSurname,pEmail,pRole;


    private static UsersWidgetUiBinder UI_BINDER = GWT.create(UsersWidgetUiBinder.class);

    public UserWidget(User user) {
        initWidget(UI_BINDER.createAndBindUi(this));
        hUserTitle.setText("User : " + user.getId());
        pName.setText("Name : " + user.getName());
        pSurname.setText("Surname : " + user.getSurname());
        pEmail.setText("Email : " +user.getEmail());
        pRole.setText("Role : " + String.valueOf(user.getRole()));
    }
}