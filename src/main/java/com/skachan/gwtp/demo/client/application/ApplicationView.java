package com.skachan.gwtp.demo.client.application;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.DataGrid;
import com.github.gwtbootstrap.client.ui.Heading;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.skachan.gwtp.demo.client.application.custom.component.SelectedUsersPopup;
import com.skachan.gwtp.demo.client.application.custom.widget.CheckBoxHeader;
import com.skachan.gwtp.demo.client.application.custom.widget.CustomSelectionCell;
import com.skachan.gwtp.demo.client.resources.MyResources;
import com.skachan.gwtp.demo.server.model.Role;
import com.skachan.gwtp.demo.server.model.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ApplicationView extends ViewWithUiHandlers<ApplicationUiHandlers> implements ApplicationPresenter.MyView {
    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    ListDataProvider<User> userDataProvider = new ListDataProvider<>();
    MultiSelectionModel<User> selectionModel = new MultiSelectionModel<>(KEY_PROVIDER);

    public static ProvidesKey<User> KEY_PROVIDER = new ProvidesKey<User>() {
        @Override
        public Object getKey(User item) {
            return item.getId();
        }
    };

    @UiField(provided = true)
    DataGrid<User> dataGrid = new DataGrid<>(20, GWT.<DataGrid.SelectableResources>create(DataGrid.SelectableResources.class));
    @UiField
    Heading hSurname, hEmail;
    @UiField
    CheckBox cbSelection, cbCheckboxes;
    @UiField
    Button btnGo;


    @Inject
    ApplicationView(Binder uiBinder) {
        MyResources.INSTANCE.gwtp().ensureInjected();
        initWidget(uiBinder.createAndBindUi(this));
        initTableColumns();
        initComponents();
    }

    @Override
    public void populateTable(List<User> list) {
        userDataProvider.getList().addAll(list);
    }

    private void initTableColumns() {
        Column<User, Boolean> checkColumn = new Column<User, Boolean>(new CheckboxCell(true, false)) {
            @Override
            public Boolean getValue(User object) {
                btnGo.setEnabled(!selectionModel.getSelectedSet().isEmpty());
                return selectionModel.isSelected(object);
            }
        };

        TextColumn<User> idColumn = new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return String.valueOf(object.getId());
            }
        };
        TextColumn<User> firstNameColumn = new TextColumn<User>() {
            @Override
            public String getValue(User user) {
                return user.getName();
            }
        };

        final List<String> roles = new ArrayList<>();
        roles.add(String.valueOf(Role.Admin));
        roles.add(String.valueOf(Role.User));

        CustomSelectionCell<User> rolesCell = new CustomSelectionCell<>(roles);

        Column<User, String> categoryColumn = new Column<User, String>(rolesCell) {
            @Override
            public String getValue(User object) {
                return object.getRole().name();
            }
        };

        categoryColumn.setFieldUpdater(new FieldUpdater<User, String>() {
            @Override
            public void update(int index, User object, String value) {
                for (String role : roles) {
                    if (role.equals(value)) {
                        object.setRole(Role.valueOf(role));
                    }
                }
            }
        });
        CheckBoxHeader checkBoxHeader = new CheckBoxHeader(selectionModel, userDataProvider);
        checkColumn.setFieldUpdater(new FieldUpdater<User, Boolean>() {
            @Override
            public void update(int index, User object, Boolean value) {
                selectionModel.setSelected(object, value);
        }
        });
        dataGrid.addColumn(checkColumn, checkBoxHeader);
        dataGrid.setColumnWidth(checkColumn, 10, Style.Unit.PX);
        dataGrid.addColumn(idColumn, "Id");
        dataGrid.setColumnWidth(idColumn, 50, Style.Unit.PX);
        dataGrid.addColumn(firstNameColumn, "Name");
        dataGrid.setColumnWidth(firstNameColumn, 50, Style.Unit.PX);
        dataGrid.addColumn(categoryColumn, "Role");
        dataGrid.setColumnWidth(categoryColumn, 50, Style.Unit.PX);
        dataGrid.setSelectionModel(selectionModel);
        userDataProvider.addDataDisplay(dataGrid);
        dataGrid.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED);

    }

    private void initComponents(){
        btnGo.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                SelectedUsersPopup popup = new SelectedUsersPopup();
                popup.addUsers(selectionModel.getSelectedSet()).show();
            }
        });

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                hEmail.setText(" " + selectionModel.getSelectedSet().iterator().next().getEmail());
                hSurname.setText(" " + selectionModel.getSelectedSet().iterator().next().getSurname());
            }
        });
    }

}
