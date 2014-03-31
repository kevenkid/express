package com.github.kevenkid.express.users.core;

import com.github.cuter44.util.dao.*;

import com.github.kevenkid.express.users.dao.*;

public class AuthController
{
    public static boolean verifyUidPass(Integer uid, String pass)
        throws EntityNotFoundException
    {
        User u = UserMgr.get(uid);
        if (u == null)
            throw(new EntityNotFoundException());

        return(
            u.pass==pass || u.pass.equals(pass)
        );
    }

    public static boolean verifyNamePass(String name, String pass)
        throws EntityNotFoundException
    {
        User u = UserMgr.get(name);
        if (u == null)
            throw(new EntityNotFoundException());

        return(
            u.pass==pass || u.pass.equals(pass)
        );
    }

}
