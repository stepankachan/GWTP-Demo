package com.skachan.gwtp.demo.shared.action;

import com.gwtplatform.dispatch.rpc.shared.Result;
import com.skachan.gwtp.demo.server.model.User;

import java.util.List;

/**
 * @author Stepan Kachan
 */
public class GetUsersResult implements Result {

    private List<User> userList;

    @SuppressWarnings("unused")
    public GetUsersResult() {
    }

    public GetUsersResult(List<User> userList) {
        this.userList = userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }
}
