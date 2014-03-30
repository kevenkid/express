package com.github.kevenkid.express.users.core;

import com.github.cuter44.util.dao.*;
import com.github.kevenkid.express.users.dao.*;

public class AdminMgr
{
  // R
    public static Admin get(Integer id)
    {
        return(
            (Admin)HiberDao.get(Admin.class, id)
        );
    }

  // C
    public static void create(Admin e)
    {
        HiberDao.save(e);

        return;
    }

    public static Admin create()
    {
        Admin e = new Admin();
        HiberDao.save(e);

        return(e);
    }

    public static Admin create(String name, String pass)
    {
        Admin e = new Admin(name, pass);
        HiberDao.save(e);

        return(e);
    }

  // U
    public static void update(Admin e)
    {
        HiberDao.update(e);
    }

  // D
    public static void delete(Integer id)
        throws EntityNotFoundException
    {
        Admin e = get(id);
        if (e == null)
            throw(new EntityNotFoundException("entity not found: "+Admin.class.getName()+"(id="+id+")"));

        //else
        HiberDao.delete(e);

        return;
    }
}

