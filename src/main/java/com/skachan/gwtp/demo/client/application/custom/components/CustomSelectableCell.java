package com.skachan.gwtp.demo.client.application.custom.components;

import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import java.util.List;

/**
 * @author Stepan Kachan
 */
public class CustomSelectableCell<T>  extends SelectionCell {

    private static final Template TEMPLATE = GWT.create(Template.class);
    private List<String> options;

    interface Template extends SafeHtmlTemplates {
        @Template("<option value=\"{0}\" title=\"{1}\">{1}</option>")
        SafeHtml optionTemplate(String key, String text);
    }

    public CustomSelectableCell(List<String> options) {
        super(options);
        this.options = options;
    }

    @Override
    public void render(Context context, String string, SafeHtmlBuilder sb) {
        sb.appendHtmlConstant("<select tabindex=\"-1\" style=\"min-width: 100px; width:100px !important; margin: 0 !important;\">");
        for(String value : options) {
            sb.append(TEMPLATE.optionTemplate(value, value));
        }
    }
}
