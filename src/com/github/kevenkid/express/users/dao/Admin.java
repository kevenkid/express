package com.github.kevenkid.express.users.dao;

import java.io.Serializable;

public class Admin extends User
    implements Serializable
{
  // SERIAL
    public static final long serialVersionUID = 1L;

  // CONSTRUCT
    public Admin()
    {
        super();

        return;
    }

    public Admin(String name, String pass)
    {
        this();

        this.name = name;
        this.pass = pass;

        return;
    }

  // HASH
    @Override
    public int hashCode()
    {
        return(super.hashCode());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return(true);

        if ((o==null) || !this.getClass().equals(o.getClass()))
            return(false);

        Admin u = (Admin)o;
        return(
            (this.id == u.id)
            || (this.id.equals(u.id))
        );
    }
}
