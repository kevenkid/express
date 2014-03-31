package com.github.kevenkid.express.parcel.dao;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Parcel
    implements Serializable
{
  // SERIAL
    public static final long serialVersionUID = 1;

  // FIELD
    public Integer id;

    public String code;

    public static Byte ACTIVE = 0;
    public static Byte FINISHED = 1;
    public static Byte TERMINATED = -1;
    public Byte status;

    public List<Checkout> checkouts;

  // CONSTRUCT
    public Parcel()
    {
        super();

        this.checkouts = new ArrayList<Checkout>();

        return;
    }

    public Parcel(String code)
    {
        this();

        this.code = code;
        this.status = ACTIVE;

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

        Parcel d = (Parcel)o;
        return(
            (this.id == d.id)
            || (this.id.equals(d.id))
        );
    }


}
