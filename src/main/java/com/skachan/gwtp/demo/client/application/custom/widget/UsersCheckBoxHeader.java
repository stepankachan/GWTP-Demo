package com.skachan.gwtp.demo.client.application.custom.widget;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.DataGrid;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Set;

/**
 * @author Stepan Kachan
 */

public class UsersCheckBoxHeader<T> extends Header {

    private final Set<T> selectionModel;
    private final ListDataProvider<T> provider;
    private DataGrid<T> dataGrid;
    private Button button;

    public UsersCheckBoxHeader(Set selectionModel, ListDataProvider provider, DataGrid dataGrid, Button button) {
        super(new CheckboxCell());
        this.selectionModel = selectionModel;
        this.provider = provider;
        this.dataGrid = dataGrid;
        this.button = button;
    }

    @Override
    public Boolean getValue() {
        return selectionModel.size() == provider.getList().size();
    }

    @Override
    public void onBrowserEvent(Cell.Context context, Element elem, NativeEvent event) {
        InputElement input = elem.getFirstChild().cast();
        Boolean isChecked = input.isChecked();
            for (T element : provider.getList()) {
                if(isChecked)
                    selectionModel.add(element);
                else
                    selectionModel.remove(element);
            }
        button.setEnabled(isChecked);
        dataGrid.redraw();
    }
}