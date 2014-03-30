package com.github.kevenkid.express.users.dao;

import java.io.Serializable;

public class User
    implements Serializable
{
  // SERIAL
    public static final long serialVersionUID = 1L;

  // FIELD
    public Integer id;
    public String name;
    public String pass;

  // CONSTRUCT
    public User()
    {
        super();

        return;
    }

    public User(String name, String pass)
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
        int hash = 17;

        if (this.id != null)
            hash = 31*hash + this.id;

        return(hash);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return(true);

        if ((o==null) || !this.getClass().equals(o.getClass()))
            return(false);

        User u = (User)o;
        return(
            (this.id == u.id)
            || (this.id.equals(u.id))
        );
    }
}
