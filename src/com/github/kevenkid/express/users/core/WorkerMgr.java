package com.github.kevenkid.express.users.core;

import com.github.cuter44.util.dao.*;
import com.github.kevenkid.express.users.dao.*;

public class WorkerMgr
{
  // R
    public static Worker get(Integer id)
    {
        return(
            (Worker)HiberDao.get(Worker.class, id)
        );
    }

  // C
    public static void create(Worker e)
    {
        HiberDao.save(e);

        return;
    }

    public static Worker create()
    {
        Worker e = new Worker();
        HiberDao.save(e);

        return(e);
    }

    public static Worker create(String name, String pass)
    {
        Worker e = new Worker(name, pass);
        HiberDao.save(e);

        return(e);
    }

  // U
    public static void update(Worker e)
    {
        HiberDao.update(e);
    }

  // D
    public static void delete(Integer id)
        throws EntityNotFoundException
    {
        Worker e = get(id);
        if (e == null)
            throw(new EntityNotFoundException("entity not found: "+Worker.class.getName()+"(id="+id+")"));

        //else
        HiberDao.delete(e);

        return;
    }
}

