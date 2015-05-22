package com.skachan.gwtp.demo.shared.action;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;
import com.skachan.gwtp.demo.server.entity.User;

import java.util.List;

/**
 * @author Stepan Kachan
 */
public class GetPersonsList extends UnsecuredActionImpl<GetPersonListResult> {

    private List<User> userList;

    public GetPersonsList() {
    }

    @Override
    public boolean isSecured() {
        return false;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
