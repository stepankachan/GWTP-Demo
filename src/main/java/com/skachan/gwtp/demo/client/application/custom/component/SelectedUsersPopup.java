package com.skachan.gwtp.demo.client.application.custom.component;

import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.ThumbnailLink;
import com.github.gwtbootstrap.client.ui.Thumbnails;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.skachan.gwtp.demo.client.application.custom.widget.UserWidget;
import com.skachan.gwtp.demo.client.resources.MyResources;
import com.skachan.gwtp.demo.server.model.User;

import java.util.Collection;

/**
 * @author Stepan Kachan
 */

public class SelectedUsersPopup extends Composite {

    private static ModalUiBinder uiBinder = GWT.create(ModalUiBinder.class);

    interface ModalUiBinder extends UiBinder<Widget, SelectedUsersPopup> {
    }

    @UiField
    Modal modal;
    @UiField
    Thumbnails contentPanel;

    @Inject
    public SelectedUsersPopup() {
        MyResources.INSTANCE.gwtp().ensureInjected();
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("btnOk")
    public void onSaveClick(ClickEvent e) {
        modal.hide();
    }

    public Modal addUsers(Collection<User> selectedUsers) {
        for (User user : selectedUsers) {
            ThumbnailLink thumbnailLink = new ThumbnailLink();
            thumbnailLink.add(new UserWidget(user));
            contentPanel.add(thumbnailLink);
        }
        return modal;
    }

}