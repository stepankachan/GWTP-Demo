package com.skachan.gwtp.demo.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface AppResources extends ClientBundle {
    interface Normalize extends CssResource {
    }

    interface Style extends CssResource {
        @ClassName("label_error")
        String labelError();

        String container();

        String box();
    }

    @Source("css/normalize.gss")
    Normalize normalize();

    @Source("css/style.gss")
    Style style();
}
