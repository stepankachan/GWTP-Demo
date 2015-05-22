package com.skachan.gwtp.demo.shared.dispatch;

import com.gwtplatform.dispatch.rpc.shared.Result;

/**
 * The result of a {@link SendTextToServerAction} action.
 */
public class SendTextToServerResult implements Result {

    private String response;

    public SendTextToServerResult(final String response) {
        this.response = response;
    }

    /**
     * For serialization only.
     */
    @SuppressWarnings("unused")
    private SendTextToServerResult() {
    }

    public String getResponse() {
        return response;
    }
}
