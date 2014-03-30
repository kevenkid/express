package com.github.kevenkid.express.exp.core;

import com.github.cuter44.util.dao.*;
import org.hibernate.criterion.*;

import com.github.kevenkid.express.exp.dao.*;

public class DealMgr
{
  // R
    public static Deal get(Integer id)
    {
        return(
            (Deal)HiberDao.get(Deal.class, id)
        );
    }

    public static Deal get(String code)
    {
        return(
            (Deal)HiberDao.get(
                DetachedCriteria.forClass(Deal.class)
                    .add(Restrictions.eq("code", code))
            )
        );
    }
  // C
    public static void save(Deal d)
    {
        HiberDao.save(d);
    }

    public static Deal create()
    {
        Deal d = new Deal();

        HiberDao.save(d);

        return(d);
    }

    public static Deal create(String code)
    {
        Deal d = new Deal(code);

        HiberDao.save(d);

        return(d);
    }

  // U
    public static void update(Deal d)
    {
        HiberDao.update(d);
    }

  // R
    public static void delete(Integer id)
    {
        Deal d = get(id);
        if (d == null)
            throw(new EntityNotFoundException("entity not found: "+Deal.class.getName()+"(id="+id+")"));

        // else
        HiberDao.delete(d);

        return;
    }
}
