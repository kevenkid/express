package com.github.kevenkid.express.exp.dao;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Deal
    implements Serializable
{
  // SERIAL
    public static final long serialVersionUID = 1;

  // FIELD
    public Integer id;

    public String code;

    public List<Checkout> checkouts;

  // CONSTRUCT
    public Deal()
    {
        super();

        this.checkouts = new ArrayList<Checkout>();

        return;
    }

    public Deal(String code)
    {
        this();

        this.code = code;

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

        Deal d = (Deal)o;
        return(
            (this.id == d.id)
            || (this.id.equals(d.id))
        );
    }


}
