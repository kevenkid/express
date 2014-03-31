package com.github.kevenkid.express.parcel.core;

import com.github.cuter44.util.dao.*;

import com.github.kevenkid.express.parcel.dao.*;
import com.github.kevenkid.express.users.dao.*;
import com.github.kevenkid.express.users.core.*;

public class CheckoutMgr
{
  // R
    public static Checkout get(Integer id)
    {
        return(
            (Checkout)HiberDao.get(Checkout.class, id)
        );
    }
  // C
    public static void save(Checkout c)
    {
        HiberDao.save(c);
    }

    public static Checkout create()
    {
        Checkout c = new Checkout();

        HiberDao.save(c);

        return(c);
    }

    public static Checkout create(Integer uid)
    {
        User u = UserMgr.get(uid);
        if (u == null)
            throw(new EntityNotFoundException());

        Checkout c = new Checkout(u);

        HiberDao.save(c);

        return(c);
    }
  // U
    public static void update(Checkout c)
    {
        HiberDao.update(c);
    }

  // R
    public static void delete(Integer id)
    {
        Checkout c = get(id);
        if (c == null)
            throw(new EntityNotFoundException("entity not found: "+Checkout.class.getName()+"(id="+id+")"));

        // else
        HiberDao.delete(c);

        return;
    }
}
