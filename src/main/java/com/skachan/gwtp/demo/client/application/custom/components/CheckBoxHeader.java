package com.skachan.gwtp.demo.client.application.custom.components;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

/**
 * @author Stepan Kachan
 */

public final class CheckBoxHeader<T> extends Header {

    private final MultiSelectionModel selectionModel;
    private final ListDataProvider<T> provider;

    public CheckBoxHeader(MultiSelectionModel selectionModel,
                          ListDataProvider provider) {
        super(new CheckboxCell());
        this.selectionModel = selectionModel;
        this.provider = provider;
    }

    @Override
    public Boolean getValue() {
        boolean allItemsSelected = selectionModel.getSelectedSet().size() == provider
                .getList().size();
        return allItemsSelected;
    }

    @Override
    public void onBrowserEvent(Cell.Context context, Element elem, NativeEvent event) {
        InputElement input = elem.getFirstChild().cast();
        Boolean isChecked = input.isChecked();
        for (T element : provider.getList()) {
            selectionModel.setSelected(element, isChecked);
        }
    }

}