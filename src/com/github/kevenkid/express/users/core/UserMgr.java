package com.github.kevenkid.express.users.core;

import com.github.cuter44.util.dao.*;
import com.github.kevenkid.express.users.dao.*;

public class UserMgr
{
  // R
    public static User get(Integer id)
    {
        return(
            (User)HiberDao.get(User.class, id)
        );
    }

  // C
    public static void save(User u)
    {
        HiberDao.save(u);

        return;
    }

    public static User create()
    {
        User u = new User();
        HiberDao.save(u);

        return(u);
    }

    public static User create(String name, String pass)
    {
        User u = new User(name, pass);
        HiberDao.save(u);

        return(u);
    }

  // U
    public static void update(User u)
    {
        HiberDao.update(u);
    }

  // D
    public static void delete(Integer id)
        throws EntityNotFoundException
    {
        User u = get(id);
        if (u == null)
            throw(new EntityNotFoundException("entity not found: "+User.class.getName()+"(id="+id+")"));

        //else
        HiberDao.delete(u);

        return;
    }
}

