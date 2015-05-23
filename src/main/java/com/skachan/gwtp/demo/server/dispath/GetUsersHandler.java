package com.skachan.gwtp.demo.server.dispath;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.skachan.gwtp.demo.server.model.Role;
import com.skachan.gwtp.demo.server.model.User;
import com.skachan.gwtp.demo.shared.action.GetUsersAction;
import com.skachan.gwtp.demo.shared.action.GetUsersResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stepan Kachan
 */
public class GetUsersHandler implements ActionHandler<GetUsersAction, GetUsersResult> {

    @Override
    public GetUsersResult execute(GetUsersAction getUsersAction, ExecutionContext executionContext) throws ActionException {
            List<User> users = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            if (i % 2 == 0)
                users.add(new User(i, "User " + i, "Surname " + i, i + "@mail.ru", Role.Admin));
            else
                users.add(new User(i, "User " + i, "Surname " + i, i + "@mail.ru", Role.User));
        }
        return new GetUsersResult(users);
    }

    @Override
    public Class<GetUsersAction> getActionType() {
        return GetUsersAction.class;
    }

    @Override
    public void undo(GetUsersAction getUsersAction, GetUsersResult getUsersResult, ExecutionContext executionContext) throws ActionException {

    }
}
