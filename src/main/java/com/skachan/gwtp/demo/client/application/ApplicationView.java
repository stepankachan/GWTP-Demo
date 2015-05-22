package com.skachan.gwtp.demo.client.application;

import com.github.gwtbootstrap.client.ui.DataGrid;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.skachan.gwtp.demo.client.application.custom.components.CustomSelectableCell;
import com.skachan.gwtp.demo.server.entity.Role;
import com.skachan.gwtp.demo.server.entity.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ApplicationView extends ViewWithUiHandlers<ApplicationUiHandlers> implements ApplicationPresenter.MyView {
    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    private ListDataProvider<User> userDataProvider = new ListDataProvider<>();
    final SelectionModel<User> selectionModel = new SingleSelectionModel<>();
    private static final Template TEMPLATE = GWT.create(Template.class);

    interface Template extends SafeHtmlTemplates {
        @Template("<option value=\"{0}\" title=\"{1}\">{1}</option>")
        SafeHtml optionTemplate(String key, String text);
    }


    @UiField
    TextBox nameField;
    @UiField
    Button sendButton;
    @UiField
    HTML error;
    @UiField(provided = true)
    DataGrid<User> dataGrid = new DataGrid<>(20, GWT.<DataGrid.SelectableResources>create(DataGrid.SelectableResources.class));


    @Inject
    ApplicationView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
        initTableColumns();
        initTableValues();
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


    private void initTableColumns() {
        Column<User, Boolean> checkColumn = new Column<User, Boolean>(new CheckboxCell(true, false)) {
            @Override
            public Boolean getValue(User object) {
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
        SelectionCell rolesCell = new SelectionCell(roles);
        CustomSelectableCell<User> csc = new CustomSelectableCell<>(roles);

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

        dataGrid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
        dataGrid.setColumnWidth(checkColumn,10, Style.Unit.PX);
        dataGrid.addColumn(idColumn,"Id");
        dataGrid.setColumnWidth(idColumn,50, Style.Unit.PX);
        dataGrid.addColumn(firstNameColumn, "Name");
        dataGrid.setColumnWidth(firstNameColumn,100, Style.Unit.PX);
        dataGrid.addColumn(categoryColumn, "Role");
        dataGrid.setColumnWidth(categoryColumn,100, Style.Unit.PX);
        dataGrid.setSelectionModel(selectionModel);
        userDataProvider.addDataDisplay(dataGrid);

    }

    private void initTableValues(){
        List<User> users = new ArrayList<>();
        users.add(new User(1l,"Stepan","Kachan","stepankachan@mail.ru",Role.Admin));
        users.add(new User(1l,"Igor","Ivahiv","ivaha@mail.ru",Role.User));
        users.add(new User(1l,"Igor","Ivahiv","ivaha@mail.ru",Role.User));
        users.add(new User(1l,"Igor","Ivahiv","ivaha@mail.ru",Role.User));
        users.add(new User(1l,"Igor","Ivahiv","ivaha@mail.ru",Role.User));
        userDataProvider.getList().addAll(users);
    }

}
