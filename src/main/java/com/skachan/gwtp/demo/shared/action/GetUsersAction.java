package com.skachan.gwtp.demo.shared.action;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

/**
 * @author Stepan Kachan
 */
public class GetUsersAction extends UnsecuredActionImpl<GetUsersResult> {

    public GetUsersAction() {
    }

    @Override
    public boolean isSecured() {
        return false;
    }

}
