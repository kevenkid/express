package com.github.kevenkid.express.parcel.dao;

import java.io.Serializable;
import java.util.Date;

import com.github.kevenkid.express.users.dao.User;

public class Checkout
    implements Serializable
{
  // SERIAL
    public static final long serialVersionUID = 1L;

  // FIELD
    public Long id;

    public Date checktime;
    public User checker;
    //public String checkloc;

  // CONSTRUCT
    public Checkout()
    {
        super();

        return;
    }

    public Checkout(User checker)
    {
        this();

        this.checktime = new Date(System.currentTimeMillis());
        this.checker = checker;

        return;
    }

  // HASH
    @Override
    public int hashCode()
    {
        int hash = 17;

        hash = 31*hash + (this.id!=null ? this.id.hashCode() : 0);

        return(hash);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return(true);

        if (o==null || !this.getClass().equals(o.getClass()))
            return(false);

        Checkout c = (Checkout)o;
        return(
            (this.id == c.id)
            || (this.id.equals(c.id))
        );
    }

}
