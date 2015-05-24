package com.skachan.gwtp.demo.client.application;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.DataGrid;
import com.github.gwtbootstrap.client.ui.Heading;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.skachan.gwtp.demo.client.application.custom.component.SelectedUsersPopup;
import com.skachan.gwtp.demo.client.application.custom.util.StyleUtils;
import com.skachan.gwtp.demo.client.application.custom.widget.CustomSelectionCell;
import com.skachan.gwtp.demo.client.application.custom.widget.UsersCheckBoxHeader;
import com.skachan.gwtp.demo.client.resources.MyResources;
import com.skachan.gwtp.demo.server.model.Role;
import com.skachan.gwtp.demo.server.model.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApplicationView extends ViewWithUiHandlers<ApplicationUiHandlers> implements ApplicationPresenter.MyView {
    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    ListDataProvider<User> userDataProvider = new ListDataProvider<>();
    MultiSelectionModel<User> selectionModel = new MultiSelectionModel<>(KEY_PROVIDER);
    private Set<User> selectedRows = new HashSet<>();

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
                return selectedRows.contains(object);
            }

            @Override
            public void render(Cell.Context context, User object, SafeHtmlBuilder sb) {
                if (cbCheckboxes.getValue()) {
                    if (selectedRows.contains(object))
                        sb.appendHtmlConstant(StyleUtils.DISABLED_CHECKED_CHECKBOX);
                    else
                        sb.appendHtmlConstant(StyleUtils.DISABLED_CHECKBOX);
                } else
                    super.render(context, object, sb);
            }
        };
        DefaultSelectionEventManager.CheckboxEventTranslator s = new DefaultSelectionEventManager.CheckboxEventTranslator();
        checkColumn.setFieldUpdater(new FieldUpdater<User, Boolean>() {
            @Override
            public void update(int index, User object, Boolean value) {
                if (value)
                    selectedRows.add(object);
                else
                    selectedRows.remove(object);
                btnGo.setEnabled(!selectedRows.isEmpty());
                dataGrid.redraw();
            }
        });


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
        UsersCheckBoxHeader usersCheckBoxHeader = new UsersCheckBoxHeader(selectedRows, userDataProvider, dataGrid, btnGo) {
            @Override
            public void render(Cell.Context context, SafeHtmlBuilder sb) {
                if (cbCheckboxes.getValue()) {
                    if (selectedRows.size() == userDataProvider.getList().size())
                        sb.appendHtmlConstant(StyleUtils.DISABLED_CHECKED_CHECKBOX);
                    else
                        sb.appendHtmlConstant(StyleUtils.DISABLED_CHECKBOX);
                } else
                    super.render(context, sb);
            }
        };

        dataGrid.addColumn(checkColumn, usersCheckBoxHeader);
        dataGrid.setColumnWidth(checkColumn, 10, Style.Unit.PX);
        dataGrid.addColumn(idColumn, "Id");
        dataGrid.setColumnWidth(idColumn, 50, Style.Unit.PX);
        dataGrid.addColumn(firstNameColumn, "Name");
        dataGrid.setColumnWidth(firstNameColumn, 50, Style.Unit.PX);
        dataGrid.addColumn(categoryColumn, "Role");
        dataGrid.setColumnWidth(categoryColumn, 50, Style.Unit.PX);
        dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<User>createDefaultManager());
        userDataProvider.addDataDisplay(dataGrid);
        dataGrid.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED);
    }

    private void initComponents() {
        btnGo.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                SelectedUsersPopup popup = new SelectedUsersPopup();
                popup.addUsers(selectedRows).show();
            }
        });

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                String usersEmail = selectionModel.getSelectedSet().isEmpty() ? " " :
                        " " + selectionModel.getSelectedSet().iterator().next().getEmail();
                String usersSurname = selectionModel.getSelectedSet().isEmpty() ? " " :
                        " " + selectionModel.getSelectedSet().iterator().next().getSurname();
                hSurname.setText(usersSurname);
                hEmail.setText(usersEmail);
            }
        });

        cbCheckboxes.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                dataGrid.redraw();
            }
        });

        cbSelection.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                if (event.getValue()){
                    dataGrid.setSelectionModel(selectionModel,
                            DefaultSelectionEventManager.<User>createWhitelistManager());
                }
                else
                    dataGrid.setSelectionModel(selectionModel,
                            DefaultSelectionEventManager.<User>createDefaultManager());
                dataGrid.redraw();
            }
        });
    }

}
