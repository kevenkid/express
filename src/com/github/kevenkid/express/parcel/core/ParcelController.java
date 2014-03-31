package com.github.kevenkid.express.parcel.core;

import com.github.cuter44.util.dao.*;

import com.github.kevenkid.express.users.dao.*;
import com.github.kevenkid.express.users.core.*;
import com.github.kevenkid.express.parcel.dao.*;

public class ParcelController
{
    public static void checkout(String code, Integer uid, Boolean finish)
        throws EntityNotFoundException, IllegalStateException
    {
        Checkout c = CheckoutMgr.create(uid);

        Parcel d = ParcelMgr.get(code);
        if (d == null)
            d = ParcelMgr.create(code);
        if (!Parcel.ACTIVE.equals(d.status))
            throw(new IllegalStateException());

        if (Boolean.TRUE.equals(finish))
            d.status = Parcel.FINISHED;

        d.checkouts.add(c);

        ParcelMgr.update(d);
    }
}
