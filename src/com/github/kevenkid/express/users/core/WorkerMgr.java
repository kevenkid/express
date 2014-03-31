package com.github.kevenkid.express.users.core;

import org.hibernate.criterion.*;
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

    public static Worker get(String name)
    {
        return(
            (Worker)HiberDao.get(
                DetachedCriteria.forClass(Worker.class)
                    .add(Restrictions.eq("name", name))
            )
        );
    }

  // C
    public static void create(Worker w)
    {
        HiberDao.save(w);

        return;
    }

    public static Worker create()
    {
        Worker w = new Worker();
        HiberDao.save(w);

        return(w);
    }

    public static Worker create(String name, String pass)
    {
        Worker w = new Worker(name, pass);
        HiberDao.save(w);

        return(w);
    }

  // U
    public static void update(Worker w)
    {
        HiberDao.update(w);
    }

  // D
    public static void delete(Integer id)
        throws EntityNotFoundException
    {
        Worker w = get(id);
        if (w == null)
            throw(new EntityNotFoundException("entity not found: "+Worker.class.getName()+"(id="+id+")"));

        //else
        HiberDao.delete(w);

        return;
    }

  // X
    public static boolean isWorker(Integer uid)
        throws EntityNotFoundException
    {
        Worker w = get(uid);

        return(
            w!=null
        );
    }

}

