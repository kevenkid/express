package com.github.kevenkid.express.exp.core;

import com.github.cuter44.util.dao.*;

import com.github.kevenkid.express.exp.dao.*;

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
