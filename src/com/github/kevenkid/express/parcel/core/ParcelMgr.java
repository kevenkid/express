package com.github.kevenkid.express.parcel.core;

import com.github.cuter44.util.dao.*;
import org.hibernate.criterion.*;

import com.github.kevenkid.express.parcel.dao.*;

public class ParcelMgr
{
  // R
    public static Parcel get(Integer id)
    {
        return(
            (Parcel)HiberDao.get(Parcel.class, id)
        );
    }

    public static Parcel get(String code)
    {
        return(
            (Parcel)HiberDao.get(
                DetachedCriteria.forClass(Parcel.class)
                    .add(Restrictions.eq("code", code))
            )
        );
    }
  // C
    public static void save(Parcel d)
    {
        HiberDao.save(d);
    }

    public static Parcel create()
    {
        Parcel d = new Parcel();

        HiberDao.save(d);

        return(d);
    }

    public static Parcel create(String code)
    {
        Parcel d = new Parcel(code);

        HiberDao.save(d);

        return(d);
    }

  // U
    public static void update(Parcel d)
    {
        HiberDao.update(d);
    }

  // R
    public static void delete(Integer id)
    {
        Parcel d = get(id);
        if (d == null)
            throw(new EntityNotFoundException("entity not found: "+Parcel.class.getName()+"(id="+id+")"));

        // else
        HiberDao.delete(d);

        return;
    }
}
