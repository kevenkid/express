package com.github.kevenkid.express.users.core;

import org.hibernate.criterion.*;
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

    public static Admin get(String name)
    {
        return(
            (Admin)HiberDao.get(
                DetachedCriteria.forClass(Admin.class)
                    .add(Restrictions.eq("name", name))
            )
        );
    }

  // C
    public static void save(Admin a)
    {
        HiberDao.save(a);

        return;
    }

    public static Admin create()
    {
        Admin a = new Admin();
        HiberDao.save(a);

        return(a);
    }

    public static Admin create(String name, String pass)
    {
        Admin a = new Admin(name, pass);
        HiberDao.save(a);

        return(a);
    }

  // U
    public static void update(Admin a)
    {
        HiberDao.update(a);
    }

  // D
    public static void delete(Integer id)
        throws EntityNotFoundException
    {
        Admin a = get(id);
        if (a == null)
            throw(new EntityNotFoundException("entity not found: "+Admin.class.getName()+"(id="+id+")"));

        //else
        HiberDao.delete(a);

        return;
    }

  // X
    public static boolean isAdmin(Integer uid)
        throws EntityNotFoundException
    {
        Admin a = get(uid);

        return(
            a!=null
        );
    }

}

