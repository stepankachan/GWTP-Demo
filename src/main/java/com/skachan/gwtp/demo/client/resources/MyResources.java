package com.skachan.gwtp.demo.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

/**
 * @author Stepan Kachan
 */
public interface MyResources extends ClientBundle {

    MyResources INSTANCE = GWT.create(MyResources.class);

    @ClientBundle.Source("gwtp.css")
    GwtpStyle gwtp();

}
